package at.fhv.sysarch.lab3.pipeline.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PushRenderer extends Push<Pair<Face, Color>, Pair<Face, Color>> {

    private final PipelineData pd;

    public PushRenderer(PipelineData pd) {
        super(null);
        this.pd = pd;
    }

    private void render(Pair<Face, Color> pair) {

        GraphicsContext ctx = pd.getGraphicsContext();

        ctx.setStroke(pair.snd());
        ctx.setFill(pair.snd());

        double[] cordX = new double[]{pair.fst().getV1().getX(), pair.fst().getV2().getX(), pair.fst().getV3().getX()};
        double[] cordY = new double[]{pair.fst().getV1().getY(), pair.fst().getV2().getY(), pair.fst().getV3().getY()};

        switch (pd.getRenderingMode()) {
            case POINT:
                ctx.fillOval(pair.fst().getV1().getX(), pair.fst().getV1().getY(), 2, 2);
                break;

            case WIREFRAME:
                ctx.strokePolygon(cordX, cordY, 3);
                break;

            case FILLED:
                ctx.strokePolygon(cordX, cordY, 3);
                ctx.fillPolygon(cordX, cordY, 3);
                break;
        }
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        render(pair);
    }
}