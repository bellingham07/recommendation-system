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

新版spring-security不再需要继承WebSecurityConfigurerAdapter，直接注入AuthenticationConfiguration，再调用getAuthenticationManager获取AuthenticationManager从而注入进IOC中
我们使用BCryptPasswordEncoder给密码加密
都将他们注入IOC中

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