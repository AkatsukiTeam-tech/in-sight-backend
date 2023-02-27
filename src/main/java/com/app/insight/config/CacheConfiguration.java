package com.app.insight.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.app.insight.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.app.insight.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.app.insight.domain.User.class.getName());
            createCache(cm, com.app.insight.domain.Authority.class.getName());
            createCache(cm, com.app.insight.domain.User.class.getName() + ".authorities");
            createCache(cm, com.app.insight.domain.City.class.getName());
            createCache(cm, com.app.insight.domain.City.class.getName() + ".regions");
            createCache(cm, com.app.insight.domain.City.class.getName() + ".appUsers");
            createCache(cm, com.app.insight.domain.City.class.getName() + ".universities");
            createCache(cm, com.app.insight.domain.Region.class.getName());
            createCache(cm, com.app.insight.domain.Region.class.getName() + ".appUsers");
            createCache(cm, com.app.insight.domain.School.class.getName());
            createCache(cm, com.app.insight.domain.School.class.getName() + ".appUsers");
            createCache(cm, com.app.insight.domain.AppUser.class.getName());
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".parentsNumbers");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".views");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".comments");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".commentAnswers");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".taskAnswers");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".mediaFiles");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".optionUsers");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".coinsUserHistories");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".appRoles");
            createCache(cm, com.app.insight.domain.AppUser.class.getName() + ".subgroups");
            createCache(cm, com.app.insight.domain.AppRole.class.getName());
            createCache(cm, com.app.insight.domain.AppRole.class.getName() + ".appUsers");
            createCache(cm, com.app.insight.domain.ParentsNumber.class.getName());
            createCache(cm, com.app.insight.domain.Group.class.getName());
            createCache(cm, com.app.insight.domain.Group.class.getName() + ".subgroups");
            createCache(cm, com.app.insight.domain.Group.class.getName() + ".subjects");
            createCache(cm, com.app.insight.domain.Subgroup.class.getName());
            createCache(cm, com.app.insight.domain.Subgroup.class.getName() + ".schedules");
            createCache(cm, com.app.insight.domain.Subgroup.class.getName() + ".appUsers");
            createCache(cm, com.app.insight.domain.Subject.class.getName());
            createCache(cm, com.app.insight.domain.Subject.class.getName() + ".modules");
            createCache(cm, com.app.insight.domain.Subject.class.getName() + ".groups");
            createCache(cm, com.app.insight.domain.Module.class.getName());
            createCache(cm, com.app.insight.domain.Module.class.getName() + ".schedules");
            createCache(cm, com.app.insight.domain.Module.class.getName() + ".appTests");
            createCache(cm, com.app.insight.domain.Module.class.getName() + ".tasks");
            createCache(cm, com.app.insight.domain.Schedule.class.getName());
            createCache(cm, com.app.insight.domain.University.class.getName());
            createCache(cm, com.app.insight.domain.University.class.getName() + ".comments");
            createCache(cm, com.app.insight.domain.University.class.getName() + ".specializations");
            createCache(cm, com.app.insight.domain.Specialization.class.getName());
            createCache(cm, com.app.insight.domain.Specialization.class.getName() + ".universities");
            createCache(cm, com.app.insight.domain.Post.class.getName());
            createCache(cm, com.app.insight.domain.Post.class.getName() + ".views");
            createCache(cm, com.app.insight.domain.Post.class.getName() + ".comments");
            createCache(cm, com.app.insight.domain.View.class.getName());
            createCache(cm, com.app.insight.domain.Comment.class.getName());
            createCache(cm, com.app.insight.domain.Comment.class.getName() + ".commentAnswers");
            createCache(cm, com.app.insight.domain.CommentAnswer.class.getName());
            createCache(cm, com.app.insight.domain.Task.class.getName());
            createCache(cm, com.app.insight.domain.Task.class.getName() + ".mediaFiles");
            createCache(cm, com.app.insight.domain.Task.class.getName() + ".taskAnswers");
            createCache(cm, com.app.insight.domain.TaskAnswer.class.getName());
            createCache(cm, com.app.insight.domain.TaskAnswer.class.getName() + ".mediaFiles");
            createCache(cm, com.app.insight.domain.MediaFiles.class.getName());
            createCache(cm, com.app.insight.domain.AppTest.class.getName());
            createCache(cm, com.app.insight.domain.AppTest.class.getName() + ".questions");
            createCache(cm, com.app.insight.domain.Question.class.getName());
            createCache(cm, com.app.insight.domain.Question.class.getName() + ".subjects");
            createCache(cm, com.app.insight.domain.Question.class.getName() + ".options");
            createCache(cm, com.app.insight.domain.Option.class.getName());
            createCache(cm, com.app.insight.domain.OptionUser.class.getName());
            createCache(cm, com.app.insight.domain.CoinsUserHistory.class.getName());
            createCache(cm, com.app.insight.domain.TokenBlackList.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
