package org.example;

import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class SchemaDirectivesSupportTest {

    @Test
    void testIt() {
        GraphQLSchema graphQLSchema = SchemaParser.newParser().schemaString("""
                        extend schema @link(url: "https://specs.apollo.dev/federation/v2.3",
                            import: ["@key", "@shareable", "@inaccessible"])
                                                
                        type Query {
                            fdrDiscovery: FdrDiscoveryResponse @shareable
                                                
                        }
                                                
                        type FdrDiscoveryResponse @key(fields: "dummyId") {
                                                
                            dummyId: String @inaccessible
                            hello: String
                        }
                        """)
                .resolvers(new QueryResolverImpl())
                .build()
                .makeExecutableSchema();

        Assertions.assertNotNull(graphQLSchema);
    }

    @Test
    void testWithoutDirectives() {
        GraphQLSchema graphQLSchema = SchemaParser.newParser().schemaString("""
                        type Query {
                            fdrDiscovery: FdrDiscoveryResponse
                                                
                        }
                                                
                        type FdrDiscoveryResponse {    
                            dummyId: String 
                            hello: String
                        }
                        """)
                .resolvers(new QueryResolverImpl())
                .build()
                .makeExecutableSchema();

        Assertions.assertNotNull(graphQLSchema);
    }

}
