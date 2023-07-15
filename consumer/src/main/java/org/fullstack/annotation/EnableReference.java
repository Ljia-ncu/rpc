package org.fullstack.annotation;

import org.fullstack.core.ReferenceRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ReferenceRegistry.class)
public @interface EnableReference {
    String[] basePackages() default {};
}
