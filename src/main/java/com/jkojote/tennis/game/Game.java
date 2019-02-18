package com.jkojote.tennis.game;

import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.window.Window;
import com.jkojote.tennis.game.objects.Ball;
import com.jkojote.tennis.game.objects.platform.Platform;
import com.jkojote.tennis.game.objects.platform.PlatformController;
import com.jkojote.tennis.game.physics.BallPlatformCollisionResolver;
import com.jkojote.tennis.game.physics.BallSurfaceCollisionResolver;
import com.jkojote.tennis.game.physics.CollisionResolver;
import com.jkojote.tennis.game.physics.Surface;
import com.jkojote.tennis.game.ui.TextBlock;
import com.jkojote.tennis.game.ui.UiElement;
import static com.jkojote.linear.engine.window.Window.WindowBuilder;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game implements Runnable {
    private static int MIN_WIN_HEIGHT = 200, MIN_WIN_WIDTH = 200;
    private GameLoop gameLoop;
    private Window window;
    private GraphicsEngine graphicsEngine;
    private PlatformController platformController;
    private GameField gameField;
    private CollisionResolver<Ball, Surface> ballSurfaceCollisionResolver;
    private CollisionResolver<Ball, Platform> ballPlatformCollisionResolver;
    private List<Releasable> releasableResources;
    private Map<String, UiElement> ui;
    private FontMap font;
    private int score = 0;
    private boolean suspended;

    Game(int width, int height) {
        width = width < MIN_WIN_WIDTH ? MIN_WIN_WIDTH : width;
        height = height < MIN_WIN_HEIGHT ? MIN_WIN_HEIGHT: height;
        window = WindowBuilder.createWindow("tennis", width, height)
                .setAntialiasingEnabled(true)
                .setResizable(false)
                .build();
        gameLoop = new GameLoop(window);
        graphicsEngine = new PrimitiveGraphicsEngineImpl();
        releasableResources = new ArrayList<>();
        gameField = new GameField(width, height - 64);
        platformController = new PlatformController(gameField.getPlatform());
        ballSurfaceCollisionResolver = new BallSurfaceCollisionResolver();
        ballPlatformCollisionResolver = new BallPlatformCollisionResolver();
        ui = new HashMap<>();
        createResources();
        createUi(width, height);
        createLogic();
        setRenderCallback();
        releasableResources.add(graphicsEngine);
        releasableResources.add(window);
        graphicsEngine.setCamera(new StaticCamera(window));
        window.setKeyCallback(platformController);
    }

    private void createResources() {
        createFont();
    }

    private void createLogic() {
        gameLoop.setUpdateCallback(() -> {
            Ball ball = gameField.getBall();
            Platform platform = gameField.getPlatform();
            gameField.checkBallIsWithinBorders();
            gameField.checkPlatformIsWithinBorders();
            gameField.checkAndResolveBorderCollision(ballSurfaceCollisionResolver);
            if (ball.collides(gameField.getBottomBorder())) {
                System.out.println(true);
                endGame();
            }
            if (ball.collides(platform)) {
                ballPlatformCollisionResolver.resolve(ball, platform);
                ball.increaseVelocity(0.025f);
                increaseScore();
            }
            platformController.update();
            ball.move();
        });
    }

    private void endGame() {
        gameLoop.stop();
    }

    private void increaseScore() {
        score++;
        TextBlock scoreBlock = (TextBlock) ui.get("score");
        scoreBlock.setText("score: " + score);
    }

    private void setRenderCallback() {
        gameLoop.setRenderCallback(() -> {
            graphicsEngine.render(gameField.getBall().getGraphicRepresentation());
            graphicsEngine.render(gameField.getPlatform().getGraphicRepresentation());
            for (UiElement uiElement : ui.values()) {
                graphicsEngine.render(uiElement.getGraphicRepresentation());
            }
        });
    }

    private void initGame() {
        window.init();
        graphicsEngine.init();
        font.init();
    }

    private void createFont() {
        try {
            font = FontMap.FontMapBuilder.aFont()
                    .fromFile("src/main/resources/lunchds.ttf")
                    .withFontFormat(Font.TRUETYPE_FONT)
                    .withSize(32)
                    .withAntialiasingEnabled()
                    .build();
            releasableResources.add(font);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void createUi(int windowWidth, int windowHeight) {
        TextBlock score = new TextBlock(font);
        score.setRGBColor(206, 173, 70);
        score.setText("score: 0");
        score.setTranslation(new Vec3f(0, windowHeight / 2f, 0));
        ui.put("score", score);
    }

    @Override
    public void run() {
        try {
            initGame();
            gameLoop.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResources(releasableResources);
        }
    }

    private void releaseResources(Collection<Releasable> releasableResources) {
        for (Releasable r : releasableResources)
            tryRelease(r);
    }

    private void tryRelease(Releasable releasable) {
        try {
            if (releasable == null)
                return;
            releasable.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
