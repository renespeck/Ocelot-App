package org.dice_team.ocelot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aksw.ocelot.application.App;
import org.aksw.ocelot.generalisation.graph.ColoredDirectedGraph;
import org.aksw.ocelot.generalisation.graph.IColoredEdge;
import org.aksw.ocelot.generalisation.graph.INode;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
  public static final Logger LOG = LogManager.getLogger(Main.class);

  public static void trees(String predicate) {
    App app = new App();
    JSONArray re = new JSONArray();
    Map<ColoredDirectedGraph, Set<ColoredDirectedGraph>> e = app.trees(predicate);
    for (ColoredDirectedGraph ee : e.keySet()) {
      String t = convertGraphToJson(ee);
      re.put(new JSONObject(t));
      // LOG.info(ee);
    }

    System.out.println(re.toString(2));
  }

  public static void main(final String[] args) {
    LogManager.resetConfiguration();
    LogManager.getRootLogger().setLevel(Level.OFF);

    if (args.length == 1) {
      // here we switch to print generalized trees for the given predicate
      // e.g., birthPlace
      trees(args[0]);
      // } else if (args.length == 0) {
      // trees("department");
      // return;
    } else {

      String sentence = args[0];
      String d = args[1];
      String r = args[2];

      int ss, es, so, eo;
      try {
        ss = Integer.parseInt(args[3]);
        es = Integer.parseInt(args[4]);
        so = Integer.parseInt(args[5]);
        eo = Integer.parseInt(args[6]);
      } catch (NumberFormatException e) {
        LOG.error("Error: Integer parameters must be valid numbers.");
        return;
      }

      App app = new App();
      Set<String> e = app.run(sentence, d, r, ss, es, so, eo);
      List<String> mainList = new ArrayList<String>();
      mainList.addAll(e);
      System.out.println(new JSONArray(mainList));
    }
  }

  public static String convertGraphToJson(ColoredDirectedGraph graph) {
    JSONObject graphJson = new JSONObject();
    JSONArray nodesArray = new JSONArray();
    JSONArray edgesArray = new JSONArray();

    for (INode node : graph.vertexSet()) {
      JSONObject nodeJson = new JSONObject();
      nodeJson.put("id", node.getId());
      nodeJson.put("label", node.getLabel());
      nodesArray.put(nodeJson);
    }

    for (IColoredEdge edges : graph.getEdges()) {
      JSONObject edgeJson = new JSONObject();
      edgeJson.put("source", graph.getEdgeSource(edges));
      edgeJson.put("target", graph.getEdgeTarget(edges));
      edgesArray.put(edgeJson);
    }

    graphJson.put("nodes", nodesArray);
    graphJson.put("edges", edgesArray);

    return graphJson.toString(2);
  }
}
