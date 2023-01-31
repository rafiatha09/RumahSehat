package apap.tugaskelompok.rumahsehat.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import apap.tugaskelompok.rumahsehat.security.jwtutils.JwtAuthenticationEntryPoint;
import apap.tugaskelompok.rumahsehat.security.jwtutils.JwtFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtFilter jwtRequestFilter;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Configuration
    @Order(1)
    public class RestApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        protected void configure(HttpSecurity http) throws Exception {
            // We don't need CSRF for this example
            http
                    .antMatcher("/api/**").cors()
                    .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/v1/auth/login").permitAll()
                    .antMatchers("/api/v1/list-appointment").permitAll()
                    .antMatchers("/api/v1/list-dokter").permitAll()
                    .antMatchers("/api/v1/list-pasien").permitAll()
                    .antMatchers("/api/v1/list-apoteker").permitAll()
                    .antMatchers("/api/v1/list-obat").permitAll()
                    .antMatchers("/api/v1/pasien/add").permitAll()
                    .antMatchers("/api/v1/**").permitAll() // hanya untuk local
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .httpBasic();

            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    String admin = "Admin";
    String dokter = "Dokter";
    String apoteker = "Apoteker";
    @Configuration
    @Order(2)
    public class UILoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .addFilterBefore(jwtRequestFilter, SessionManagementFilter.class)
                    .authorizeRequests()
                    .antMatchers("/create_dummy").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/login-sso", "/validate-ticket", "/non-whitelist-error").permitAll()
                    .antMatchers("/user").hasAuthority(admin)
                    .antMatchers("/pasien/viewall").hasAuthority(admin)
                    .antMatchers("/dokter/viewall").hasAuthority(admin)
                    .antMatchers("/apoteker/viewall").hasAuthority(admin)
                    .antMatchers("/dokter/add").hasAuthority(admin)
                    .antMatchers("/apoteker/add").hasAuthority(admin)
                    .antMatchers("/appointment/viewall").hasAnyAuthority(admin, dokter)
                    .antMatchers("/resep").hasAnyAuthority(admin, apoteker)
                    .antMatchers("/obat/viewall").hasAnyAuthority(admin, apoteker)
                    .antMatchers("/obat/ubah-stock/**").hasAuthority(apoteker)
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login").permitAll()
                    .and()
                    .sessionManagement().sessionFixation().newSession().maximumSessions(1);
        }
    }
}
