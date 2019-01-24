package com.zishanfu.vistrips.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.spark.api.java.JavaRDD;
import org.jxmapviewer.JXMapViewer;

import com.zishanfu.vistrips.osm.OsmGraph;
import com.zishanfu.vistrips.sim.TrafficModelPanel;
import com.zishanfu.vistrips.sim.World;
import com.zishanfu.vistrips.sim.model.IDMVehicle;

public class SimulationBtnHandler implements ActionListener{

	private JXMapViewer mapViewer;
	private JavaRDD<IDMVehicle> vehicles;
	private OsmGraph graph;
	
	public SimulationBtnHandler(JXMapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}
	
	public void setVehicles(JavaRDD<IDMVehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public void setGraph(OsmGraph graph) {
		this.graph = graph;
	}

	public void actionPerformed(ActionEvent e) {
		if(vehicles == null || vehicles.count() == 0) {
			AttentionDialog dialog = new AttentionDialog("Attention", 
					"Please wait to generate trips!");
		}else {
//			int delay = (int)(delayInSec*1000);
//			final Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>();
//			
//			for(Pair p: pairs) {
//				if(p == null) {
//					continue;
//				}
//				waypoints.add(new MyWaypoint(Color.black, p.getRoute()));
//			}
//			
//			final WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
//			waypointPainter.setWaypoints(waypoints);
//			mapViewer.setOverlayPainter(waypointPainter);
//			waypointPainter.setRenderer(new PointRender());
//
//			Timer timer = new Timer(delay, new ActionListener() {
//	            public void actionPerformed(ActionEvent e) {
//	                for (MyWaypoint waypoint: waypoints) {
//	                	waypoint.update();
//	                }
//	                mapViewer.repaint();
//	            }
//	        });
//
//	        timer.start();
			World world = new World(graph, vehicles.rdd());
			
			//set simulation time
			try {
				new TrafficModelPanel(world).run(1);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		} 
		
	}

}
