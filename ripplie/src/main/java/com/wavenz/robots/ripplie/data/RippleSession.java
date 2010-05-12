package com.wavenz.robots.ripplie.data;

import net.fortytwo.linkeddata.sail.LinkedDataSail;
import net.fortytwo.ripple.Ripple;
import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.URIMap;
import net.fortytwo.ripple.cli.TurtleView;
import net.fortytwo.ripple.model.Model;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.impl.sesame.SesameModel;
import net.fortytwo.ripple.query.LazyStackEvaluator;
import net.fortytwo.ripple.query.QueryEngine;
import net.fortytwo.ripple.query.QueryPipe;
import net.fortytwo.ripple.query.StackEvaluator;
import net.fortytwo.ripple.util.HTTPUtils;
import org.apache.log4j.Logger;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;
import org.openrdf.sail.memory.MemoryStore;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RippleSession {
    private static final Logger LOG = Logger.getLogger(RippleSession.class);

    public String evaluate(String script) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(stream);

            Properties properties = new Properties();
            properties.put(Ripple.HTTPCONNECTION_TIMEOUT, "5000");
            Ripple.initialize(properties);
            
            Sail baseSail = new MemoryStore();
            baseSail.initialize();

            // Instantiate LinkedDataSail.
            URIMap uriMap = new URIMap();
            Sail sail = new LinkedDataSail(baseSail, uriMap);
            sail.initialize();

            // Attach a Ripple model to the repository.
            Model model = new SesameModel(sail, Ripple.class.getResource("libraries.txt"), uriMap);

            // Attach a query engine to the model.
            StackEvaluator evaluator = new LazyStackEvaluator();
            QueryEngine queryEngine = new QueryEngine(model, evaluator, printStream, printStream);

            // Create a connection to thew Ripple model.
            ModelConnection mc = queryEngine.getConnection();

            // This is a "sink" which counts and displays all of the resources which
            // are piped into it.  We'll use it to display query results.
            TurtleView view = new TurtleView(queryEngine.getPrintStream(), mc);

            // Add a parser pipeline for evaluating plain-text queries.
            // Feed results into the "Turtle view"
            QueryPipe queryPipe = new QueryPipe(queryEngine, view);

            LOG.debug("Evaluate script: " + script);
            queryPipe.put(script);

            // All done.
            LOG.debug("Close model connection");
            mc.close();
            LOG.debug("Shutdown linked data sail");
            sail.shutDown();
            LOG.debug("Shutdown memory store");
            baseSail.shutDown();

            LOG.debug("Writing output");
            return stream.toString();
        } catch (RippleException e) {
            LOG.error(e.getMessage(), e);
            return e.getMessage();
        } catch (SailException e) {
            LOG.error(e.getMessage(), e);
            return e.getMessage();
        }
    }
}
