package cn.papudding.uums.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.test.BeforeOAuth2Context;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private AuthorizationCodeServices authorizationCodeServices; // 授权码服务
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        //return new JdbcAuthorizationCodeServices(dataSource);
        return new InMemoryAuthorizationCodeServices();
    }
//    @Resource
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private JwtAccessTokenConverter accessTokenConverter;

    //令牌管理服务
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(jdbcClientDetailsService());
        defaultTokenServices.setReuseRefreshToken(true);
        defaultTokenServices.setTokenStore(tokenStore);
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        defaultTokenServices.setAccessTokenValiditySeconds(7200);//2hours
        defaultTokenServices.setRefreshTokenValiditySeconds(25920);//3days
        return defaultTokenServices;
    }
    //令牌访问端点和令牌服务
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//密码模式
                .accessTokenConverter(accessTokenConverter)// 配置JwtAccessToken转换器
//                .authorizationCodeServices(authorizationCodeServices)//授权码模式
                .tokenServices(authorizationServerTokenServices())//令牌管理模式
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);

    }
    /****************************************************************************************************************/
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private ClientDetailsService clientDetailsService;
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }
    @Autowired
    private DataSource dataSource;

    //客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
        //clients.jdbc(dataSource);
//        clients.inMemory().withClient("PApudding")
//                .secret(passwordEncoder.encode("123456"))
//                .scopes("All")
//                .redirectUris("http://www.baidu.com")
//                .autoApprove(false)
//                .authorizedGrantTypes("authorization_code","password");
    }

    /****************************************************************************************************************/
    //令牌端点安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * /oauth/authorize : 授权端点
         * /oauth/token : 令牌端点
         * /oauth/confirm_access : 用户确认授权提交端点
         * /oauth/error : 授权服务错误信息端点
         * /oauth/check_token : 用于资源服务访问的令牌解析端点
         * /oauth/token_key : 提供公钥的端点，如果使用的是JWT令牌
         */
        security
                .allowFormAuthenticationForClients() //允许表单认证
                .tokenKeyAccess("permitAll()") //token端点完全公开
                .checkTokenAccess("permitAll()"); //check_token端点完全公开
    }
}
