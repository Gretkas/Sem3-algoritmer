
package oving8;
import java.util.*;

import javax.swing.JFrame;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;


public class Map {

        public static void generateMap(Node[] nodes,boolean drawRoute){

            JXMapViewer mapViewer = new JXMapViewer();

            // Display the viewer in a JFrame
            JFrame frame = new JFrame("Rutekart");
            frame.getContentPane().add(mapViewer);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Create a TileFactoryInfo for OpenStreetMap
            TileFactoryInfo info = new OSMTileFactoryInfo();
            DefaultTileFactory tileFactory = new DefaultTileFactory(info);
            mapViewer.setTileFactory(tileFactory);



            // Create a track from the geo-positions
            List<GeoPosition> track = new ArrayList<>();
            HashSet<Waypoint> waypoints = new HashSet();
            if(drawRoute){
                waypoints.add(new DefaultWaypoint(new GeoPosition(nodes[0].getLatitude(), nodes[0].getLongitude())));
                for (int i = 0; i < nodes.length; i++) {
                    GeoPosition gp = new GeoPosition(nodes[i].getLatitude(), nodes[i].getLongitude());
                    track.add(gp);
                }
                waypoints.add(new DefaultWaypoint(new GeoPosition(nodes[nodes.length-1].getLatitude(), nodes[nodes.length-1].getLongitude())));
            }else {
                for (int i = 0; i < nodes.length; i++) {
                    GeoPosition gp = new GeoPosition(nodes[i].getLatitude(), nodes[i].getLongitude());
                    track.add(gp);
                    waypoints.add(new DefaultWaypoint(gp));
                }
            }


            // Set the focus
            mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

            // Create a waypoint painter that takes all the waypoints
            WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
            waypointPainter.setWaypoints(waypoints);

            // Create a compound painter that uses both the route-painter and the waypoint-painter
            List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
            if(drawRoute) {
                RoutePainter routePainter = new RoutePainter(track);
                painters.add(routePainter);
            }
            painters.add(waypointPainter);

            CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
            mapViewer.setOverlayPainter(painter);
        }

        public static void generateMap(LinkedList<Node> nodeList){
            Node[] nodes = new Node[nodeList.size()];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = nodeList.removeFirst();
            }
            generateMap(nodes,true);
        }

        public static void generateMapGAS(LinkedList<NodeAStar> nodeList){
            Node[] nodes = new Node[nodeList.size()];
            for (int i = 0; i < nodes.length; i++) {
                NodeAStar n = nodeList.removeFirst();
                nodes[i] = new Node(n.getNodeNumber(),n.getLatitude(),n.getLongitude());
            }
            generateMap(nodes,true);
        }
}
