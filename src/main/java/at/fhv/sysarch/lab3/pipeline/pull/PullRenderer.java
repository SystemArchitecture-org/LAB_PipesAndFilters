package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.OperationNotAllowedException;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullRenderer<I extends Pair<Face, Color>> extends Pull<I, Pair<Face, Color>> {

    private final PipelineData pd;

    public PullRenderer(IPull<I> predecessor, PipelineData pd) {
        super(predecessor);
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> pull() {
        throw new OperationNotAllowedException("can't pull on sink");
    }

    public void render() {

        while (predecessor.hasNext()) {
            Pair<Face, Color> pair = predecessor.pull();

            GraphicsContext ctx = pd.getGraphicsContext();

            ctx.setStroke(pair.snd());
            ctx.setFill(pair.snd());

            double[] cordX = new double[]{pair.fst().getV1().getX(), pair.fst().getV2().getX(), pair.fst().getV3().getX()};
            double[] cordY = new double[]{pair.fst().getV1().getY(), pair.fst().getV2().getY(), pair.fst().getV3().getY()};

            switch (pd.getRenderingMode()) {
                case POINT:
                    ctx.fillOval(pair.fst().getV1().getX(), pair.fst().getV1().getY(), 2 ,2);
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
    }
}