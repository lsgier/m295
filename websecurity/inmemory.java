@Bean
public UserDetailsService userDetailsService() {
  InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
  manager.createUser(User.withDefaultPasswordEncoder()
      .username("user")
      .password("password")
      .roles("USER")
      .build());
  return manager;
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http
      .authorizeHttpRequests(requests -> requests
          .requestMatchers("/user")
          .hasAnyRole("USER")
          .anyRequest()
          .permitAll()
      ).httpBasic(Customizer.withDefaults());
  return http.build();
}
