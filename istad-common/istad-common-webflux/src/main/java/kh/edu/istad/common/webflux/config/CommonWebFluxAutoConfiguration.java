package kh.edu.istad.common.webflux.config;

import kh.edu.istad.common.webflux.exception.DomainExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.DispatcherHandler;

@AutoConfiguration

// Class-level:
// Only activate if the project is a Reactive Web application
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)

// ធានាថាកម្មវិធីកំពុងដំណើរការជាប្រភេទ Reactive Web Environment
/**
 * ដើម្បីធានាថា Spring WebFlux Core Dispatcher មានវត្តមាននៅក្នុង Classpath ពិតប្រាកដមុនពេលដំណើរការ
 * Auto-Configuration នៅក្នុង Shared Module យើងត្រូវប្រើ Annotation @ConditionalOnClass
 * ដោយចង្អុលទៅកាន់ DispatcherHandler ។
 *
 * 1. គោលការណ៍គ្រឹះ
 * នៅក្នុង Spring Boot កាលណាអ្នកបង្កើត shared library (ដូចជា common-webflux) គម្រោងដទៃទៀតដែលទាញយក
 * library នេះទៅប្រើប្រាស់ ប្រហែលជាមិនមែនជា WebFlux Application ទាំងអស់នោះទេ
 * (ឧទាហរណ៍៖ ជា MVC ធម្មតា ឬជា Batch Service)។
 * ដូច្នេះ ប្រសិនបើយើងមិនដាក់លក្ខខណ្ឌការពារទេ Spring នឹងព្យាយាម instantiate beans របស់ WebFlux
 * ដែលនាំឱ្យកើតមានបញ្ហា ClassNotFoundException ឬ NoClassDefFoundError ភ្លាមៗពេល application startup។
 *
 * 2. ការអនុវត្តកូដជាក់ស្តែង
 * ចូរបន្ថែម @ConditionalOnClass(DispatcherHandler.class) នៅពីលើ Auto-Configuration Class របស់អ្នក៖
 * */
@ConditionalOnClass(DispatcherHandler.class)

public class CommonWebFluxAutoConfiguration {

    @Bean

    // if not there exists bean then create new bean
    @ConditionalOnMissingBean(DomainExceptionHandler.class)

    public DomainExceptionHandler domainExceptionHandleer() {
        return new DomainExceptionHandler();
    }

}
