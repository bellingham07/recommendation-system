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

新版spring-security不再需要继承WebSecurityConfigurerAdapter，需要的AuthenticationManager不再需要通过重写来注入bean，直接注入AuthenticationConfiguration，再调用getAuthenticationManager获取AuthenticationManager从而注入进IOC中

我们使用BCryptPasswordEncoder给密码加密

都将他们注入IOC中

将我们的JwtToken认证工具类注入进来

    @Configuration
    public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
