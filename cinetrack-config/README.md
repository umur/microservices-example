# CinéTrack Configuration

Externalized configuration for the CinéTrack microservices fleet, served by the `config-server` module via Spring Cloud Config.

Layout: `{application}/{application}.yml` and `{application}/{application}-{profile}.yml`.

In production this would live in a separate git repository so configuration changes can be reviewed and audited independently of service code. For the book's runnable example it lives alongside the services in the same repo (`umur/microservices-example`), and Config Server is pointed at `search-paths: 'cinetrack-config/{application}'` against this repo's `main`/`develop` branch.
