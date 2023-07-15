package org.fullstack.core;

import org.fullstack.annotation.EnableReference;
import org.fullstack.annotation.Reference;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ReferenceRegistry implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    private Environment environment;

    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerReferences(metadata, registry);
    }

    public void registerReferences(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidates = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Reference.class));
        Set<String> basePackages = getBasePackage(metadata);
        for (String pkg : basePackages) {
            candidates.addAll(scanner.findCandidateComponents(pkg));
        }
        for (BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition beanDefinition) {
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                registerReference(registry, annotationMetadata);
            }
        }
    }
    private void registerReference(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata) {
        try {
            String className = annotationMetadata.getClassName();
            Class<?> clazz = Class.forName(className);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ReferenceFactoryBean.class);
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className);
            BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackage(AnnotationMetadata metadata) {
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableReference.class.getCanonicalName());
        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attrs.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        return basePackages;
    }
}