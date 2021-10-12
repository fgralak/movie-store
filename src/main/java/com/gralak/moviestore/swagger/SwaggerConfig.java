package com.gralak.moviestore.swagger;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;

import java.util.List;

import static java.util.Collections.singletonList;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class SwaggerConfig
{
    @Bean
    public Docket apiDocket()
    {
        return new Docket(SWAGGER_2)
                .apiInfo(getApiInfo()).forCodeGeneration(true)
                .securityContexts(singletonList(getSecurityContext()))
                .securitySchemes(singletonList(getApiKey())).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.regex("/*/.*")).build()
                .tags(
                        new Tag("Movie Controller", "All APIs responsible for Movies"),
                        new Tag("Warehouse Controller", "All APIs responsible for Warehouses"),
                        new Tag("App User Controller", "All APIs responsible for App Users"),
                        new Tag("Movie-Warehouse Controller", "All APIs responsible for relations between Movies and Warehouses")
                );
    }

    private ApiInfo getApiInfo()
    {
        Contact contact = new Contact("Movie Store API Support", "https://www.movie-store-support.com", "movie-store@support.com");
        return new ApiInfoBuilder()
                .title("Movie Store API")
                .description("This API was created to mange a movie store business." +
                        "<br><br><strong> Note: This API requires an API KEY. Please log into your account to access your key.</strong>")
                .version("1.0.0")
                .license("Apache License 2.1.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }

    private SecurityContext getSecurityContext()
    {
        return SecurityContext.builder().securityReferences(getSecurityReference()).build();
    }

    private List<SecurityReference> getSecurityReference()
    {
        AuthorizationScope[] authorizationScope = {new AuthorizationScope("Unlimited", "Full API Permission")};
        return singletonList(new SecurityReference("JWT", authorizationScope));
    }

    private ApiKey getApiKey()
    {
        return new ApiKey("JWT", "Authorization", SecurityScheme.In.HEADER.name());
    }

    @Bean
    SecurityConfiguration security()
    {
        return SecurityConfigurationBuilder.builder()
                .clientId("test-app-client-id")
                .clientSecret("test-app-client-secret")
                .realm("test-app-realm")
                .appName("test-app")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .enableCsrfSupport(false)
                .build();
    }

    /*@Bean
    UiConfiguration uiConfig()
    {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }*/
}
