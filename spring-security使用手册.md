# spring-security用做登录功能的一些用法

## 登录思路分析

​ ①自定义登录接口

​ 调用ProviderManager的方法进行认证 如果认证通过生成jwt

​ 把用户信息存入redis中

​ ②自定义UserDetailsService

​ 在这个实现类中去查询数据库

​ 注意配置passwordEncoder为BCryptPasswordEncoder

校验：

​ ①定义Jwt认证过滤器

​ 获取token

​ 解析token获取其中的userid

​ 从redis中获取用户信息

​ 存入SecurityContextHolder

## 1.SecurityConfig配置类

新版spring-security不再需要继承WebSecurityConfigurerAdapter，需要的AuthenticationManager不再需要通过重写来注入bean，
直接注入AuthenticationConfiguration，再调用getAuthenticationManager获取AuthenticationManager从而注入进IOC中

当然这里默认是注入了AuthenticationManager就行了。因为他是一个接口，所以实际IOC会调用他的实现类去走authenticate方法，这个实现类就是ProviderManager

ProviderManager管理了一个AuthenticationProvider列表，每个AuthenticationProvider都是一个认证器，
不同的 AuthenticationProvider 用来处理不同的 Authentication 对象的认证。可以看到源码中，增强for循环中就在对不同的AuthenticationProvider执行authenticate方法。

AuthenticationProvider也是一个接口，那么他的实现类就是一个抽象类AbstractUserDetailsAuthenticationProvider，其中的authenticate方法不是抽象方法，所以authenticate还是在这执行的，
这个抽象类有一个抽象方法是retrieveUser，authenticate方法中也调用了他，实现是在继承这个抽象类的是DaoAuthenticationProvider中重写了。

然后会去调用默认的DaoAuthenticationProvider的authenticate方法，这个就是实现了AbstractUserDetailsAuthenticationProvider的默认实现类，

默认的UserDetailService有InMemoryUserDetailManager和JDBCUserDetailManager，他们都是实现了UserDetailsManager这个接口的，而这个接口又是继承了UserDetailService，
所以我们自定义的重写了loadUserByUsername方法的类就是实现了UserDetailService的实现类，至于怎么获取我们的实现类，下面的config中有写到，因为我们首先加上了@Component注解
，获取bean，再到DaoAuthenticationProvider中加入该实现类，我们调用setUserDetailsService方法传入我们的UserDetailService实现类就成功了，PasswordEncoder也是如此。

所以实现的流程是

接口： ​ ​ ​ ​   AbstractAuthenticationProcessingFilter ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ->​ ​ ​ ​  ​ ​ ​ ​ ​ AuthenticationManager ​ ​ ​ ​ ​ ->  ​ ​ ​ ​ ​ AbstractUserDetailsAuthenticationProvider -> UserDetailsService



实现类： UsernamePasswordAuthenticationFilter.authenticate() -> ProviderManager.authenticate() -> DaoUserDetailsService.retrieveUser() -> UserDetailsService.loadUserByUsername()

将我们的JwtToken认证过滤器注入进来

    @Configuration
    public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    // @Bean
    // public AuthenticationManager authenticationManager() throws Exception {
        // return authenticationConfiguration.getAuthenticationManager();
    // }

    @Autowired
    @Qualifier("celebrity")
    private UserDetailsService celebrityDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider celebrityDao = new DaoAuthenticationProvider();
        celebrityDao.setPasswordEncoder(passwordEncoder());
        celebrityDao.setUserDetailsService(celebrityDetailsService);
        return new ProviderManager(celebrityDao);
    }

    // 将passwordEncoder配置为BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        }
    }

## 2.JwtAuthenticationTokenFilter

思路
​ ①定义Jwt认证过滤器

​ 获取token

​ 解析token获取其中的userid

​ 从redis中获取用户信息

​ 存入SecurityContextHolder

此处的jwttoken认证过滤器我们这样写

    @Component
    public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 1.获取请求头中的token
        String token = httpServletRequest.getHeader("token");
        if (!StrUtil.isBlank(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        // 2.解析获取网红Id
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 2.1.token超时 token非法
            // 2.2.响应告诉前端需要重新登录
            Result result = Result.error("需要登录后操作！");
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        String id = claims.getSubject();
        // 3.从redis中获取用户信息
        Map celebrityCacheMap = redisClient.getMapCache("celebrity:" + id);
        LoginCelebrity celebrity = BeanUtil.fillBeanWithMap(celebrityCacheMap, new LoginCelebrity(), true);
        // 3.1.如果获取不到
        if (ObjectUtil.isNull(celebrity)) {
            // 3.2.说明登录过期 提示重新登录
            Result result = Result.error("登录信息过期，请重新登录！");
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        // 4.存入securityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(celebrity, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

# 3. 认证授权失败处理

目前我们的项目在认证出错或者权限不足的时候响应回来的Json是Security的异常处理结果。但是这个响应的格式肯定是不符合我们项目的接口规范的。所以需要自定义异常处理。

## AuthenticationEntryPoint 认证失败处理器

    @Component
    public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            e.printStackTrace();
            Result result = null;
            if (e instanceof BadCredentialsException) {
            result = Result.error(LOGIN_INFO_ERROR);
        } else if (e instanceof InsufficientAuthenticationException) {
            result = Result.error(AUTHENTICATION_OR_AUTHORIZATION_FAIL);
        }
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
        }
    }


## AccessDenialHandler 授权失败处理器

    @Component
    public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        Result result = Result.error(NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
        }
    }
