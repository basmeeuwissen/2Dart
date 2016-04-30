/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uitdehoogte.twodart;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.hubspot.dropwizard.guice.GuiceBundle.Builder;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.uitdehoogte.twodart.model.Game;
import nl.uitdehoogte.twodart.model.Player;
import nl.uitdehoogte.twodart.model.Throw;
import nl.uitdehoogte.twodart.model.Turn;
import nl.uitdehoogte.twodart.services.AuthenticationService;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 *
 * @author RG79YA
 */
public class App extends Application<Config>
{
    private Module module;
    private GuiceBundle guiceBundle;
    private HibernateBundle<Config> hibernateBundle;

    @Override
    public void initialize(Bootstrap<Config> bootstrap)
    {
        hibernateBundle = initializeHibernate();
        module = new Module(hibernateBundle);
        guiceBundle = initializeGuice(Config.class, module);
        
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(guiceBundle);
        
        bootstrap.addBundle((ConfiguredBundle)new ConfiguredAssetsBundle("/assets/", "/client", "index.html"));
    }

    private GuiceBundle initializeGuice(Class<Config> configurationClass, Module module)
    {
        Builder guiceBuilder = GuiceBundle.<Config>newBuilder()
                .addModule(module)
                .enableAutoConfig(new String[]
                {
                    "nl.uitdehoogte.twodart.resources"
                })
                .setConfigClass(configurationClass);

        return guiceBuilder.build();
    }

    private HibernateBundle<Config> initializeHibernate()
    {
        return new HibernateBundle<Config>(Player.class, Game.class, Turn.class, Throw.class)
        {
            @Override
            public DataSourceFactory getDataSourceFactory(Config configuration)
            {
                return configuration.getDataSourceFactory();
            }
        };
    }

    private void initializeAuthentication(Config configuration, Environment environment)
    {
        AuthenticationService authenticationService = guiceBundle.getInjector().getInstance(AuthenticationService.class);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<Player>()
                .setAuthenticator(authenticationService)
                .setAuthorizer(authenticationService)
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Player.class));
    }

    @Override
    public void run(Config configuration, Environment environment) throws Exception
    {
        initializeAuthentication(configuration, environment);
    }

    public static void main(String[] args) throws Exception
    {
        new App().run(args);
    }
}
