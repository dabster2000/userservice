package dk.trustworks.userservice;

/**
 * A simple hello world service app entry
 *
 * Run this app, try to update some of the code, then
 * press F5 in the browser to watch the immediate change
 * in the browser!
 */
@SuppressWarnings("unused")
public class AppEntry {

    //@Inject
    //private UserQueryRepository userQueryRepository;
/*
    @OnAppStart
    public void ensureTestingData() {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration("userservice").withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(configuration.getTracer());
    }

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Locale.setDefault(new Locale("da", "DK"));

        Act.start();
    }
    */

}
