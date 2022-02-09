package dandelion.persists.dialect;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.engine.jdbc.dialect.internal.DialectResolverSet;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

/** @author Marcus */
@Slf4j
public class SqliteMetadataBuilderInitializer implements MetadataBuilderInitializer {

  public static final String NAME = "SQLite";

  @Override
  public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry serviceRegistry) {
    DialectResolver dialectResolver = serviceRegistry.getService(DialectResolver.class);

    if (!(dialectResolver instanceof DialectResolverSet)) {
      log.warn(
          "DialectResolver '{}' is not an instance of DialectResolverSet, not registering SQLiteDialect",
          dialectResolver);
      return;
    }

    ((DialectResolverSet) dialectResolver).addResolver(RESOLVER);
  }

  private static final SqliteDialect DIALECT = new SqliteDialect();

  private static final DialectResolver RESOLVER =
      info -> {
        if (info.getDatabaseName().equals(NAME)) {
          return DIALECT;
        }

        return null;
      };
}
