package hello.ssia_ch2_ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 클래스를 구성 클래스로 표시
public class ProjectConfig {
    @Bean // 반환된 값을 스프링 컨텍스트에 빈으로 추가하도록 지시
    public UserDetailsService userDetailsService() {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("john") // 주어진 사용자 이름, 암호, 권한 목록으로 사용자 생성
                .password("12345")
                .authorities("read")
                .build();

        userDetailsService.createUser(user); // UserDetailsService 에서 관리하도록 사용자 추가

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        // NoOpPasswordEncoder 인스턴스는 암호에 암호화나 해시를 적용하지 않고 일반 텍스트처럼 처리
        // 운영단계에서는 이용하면 안된다.
        // 예제에서 암호의 해싱 알고리즘에 신경 쓰고 싶지 않을 때 좋은 옵션이다.
        // 클래스의 개발자가 이를 고려해 @Deprecated를 지정했기 때문에 개발 환경에는 클래스이름에 취소선 표시
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated() // 모든 요청에 인증이 필요하다.
                        .anyRequest().permitAll() // 인증 없이 요청할 수 있다.
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
