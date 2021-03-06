package no.uib.ii.algo.st8;

import no.uib.ii.algo.st8.start.Coordinate;
import no.uib.ii.algo.st8.util.VectorSpaceBasis;

import org.jgrapht.graph.SimpleGraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class GraphView extends View {

	private SimpleGraph<DefaultVertex, DefaultEdge<DefaultVertex>> graph;
	private String info = "";
	private VectorSpaceBasis basis;

	public GraphView(Context context) {
		super(context);
		basis = new VectorSpaceBasis();
		System.out.println("done!?!");

		System.out.println("GraphView initialized");

		setFocusable(true);
	}

	public void redraw(String info,
			SimpleGraph<DefaultVertex, DefaultEdge<DefaultVertex>> graph,
			VectorSpaceBasis basis) {
		this.info = info;
		this.basis = basis;
		this.graph = graph;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (graph == null)
			return;
		for (DefaultEdge<DefaultVertex> e : graph.edgeSet()) {
			Coordinate c1 = basis.transform(e.getSource().getCoordinate());
			Coordinate c2 = basis.transform(e.getTarget().getCoordinate());

			Coordinate ce = basis.transform(e.getCoordinate());

			Paint p = new Paint();
			p.setColor(Color.WHITE);
			p.setColor(e.getColor());
			if (ce == null) {
				canvas.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY(), p);
			} else {
				canvas.drawArc(
						new RectF(c1.getX(), c1.getY(), c2.getX(), c2.getY()),
						0, 0, true, p);
			}

		}

		for (DefaultVertex v : graph.vertexSet()) {
			Coordinate c = basis.transform(v.getCoordinate());
			Paint p = new Paint();
			p.setColor(v.getColor());
			canvas.drawCircle(c.getX(), c.getY(), v.getSize(), p);
		}
		writeInfo(canvas);
	}

	private void writeInfo(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		canvas.drawText(info, 10, 10, p);
	}

}
