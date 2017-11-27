package com.mctok.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture backgraund;
	Texture bird;
	Texture bird2;
	Texture bird2_2;
	Texture bird2_3;
	float birdX =0;
	float birdY =0;
	int gameState =0;
	float velocity =0;
	float gravity =0.2f;
	float enemyVelocity =2;
	Random random;
	int score =0;
	int scoredEnemy=0;
	BitmapFont font ;
	BitmapFont font2;

	Circle birdCircle ;

	ShapeRenderer shapeRenderer;


	int numberOfEnemies =4;
	float [] enemyx = new float[numberOfEnemies];
	float [] enemyOffset = new float[numberOfEnemies];
	float [] enemyOffset2 = new float[numberOfEnemies];
	float [] enemyOffset3= new float[numberOfEnemies];

	Circle[] enemyCircle;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;


	float distance = 0;



	@Override
	public void create () {
		batch =  new SpriteBatch(  );
		backgraund = new Texture( "backgraund.png" );
		bird = new Texture( "bird.png" );
		bird2 = new Texture( "bird2.png" );
		bird2_2 = new Texture( "bird2.png" );
		bird2_3 = new Texture( "bird2.png" );

		distance = Gdx.graphics.getWidth()/2;
		random = new Random( );

		birdX = Gdx.graphics.getWidth()/2-bird.getHeight()/2;
		birdY = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer(  );

		birdCircle = new Circle(  );
		enemyCircle = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];

		font = new BitmapFont(  );
		font.setColor( Color.WHITE );
		font.getData().setScale( 4 );

		font2 = new BitmapFont(  );
		font2.setColor( Color.WHITE );
		font2.getData().setScale( 6 );

		for(int i=0 ; i<numberOfEnemies ; i++){


			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()- 200);
			enemyOffset2[i] = (random.nextFloat() -0.5f) * (Gdx.graphics.getHeight()- 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()- 200);

			enemyx[i] = Gdx.graphics.getWidth() - bird2.getWidth()/2 +i * distance;


			enemyCircle[i] = new Circle(  );
			enemyCircle2[i] = new Circle(  );
			enemyCircle3[i] = new Circle(  );
		}

	}

	@Override
	public void render () {
		batch.begin();

		batch.draw( backgraund,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );


		if ( gameState==1 ){

			if ( enemyx[scoredEnemy]< birdX ){
				score ++;

				if ( scoredEnemy < numberOfEnemies -1 ){
					scoredEnemy ++;
				}else{
					scoredEnemy=0;
				}
			}


			if ( Gdx.input.justTouched() ){
				velocity = -8;

			}

			for (int i=0 ; i<numberOfEnemies ; i++){

				if ( enemyx[i] < Gdx.graphics.getWidth()/15 ){
					enemyx[i] = enemyx[i] + numberOfEnemies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset2[i] = (random.nextFloat() -0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);

				}else {
					enemyx[i] = enemyx[i] - enemyVelocity;
				}

				batch.draw( bird2,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffset[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10 );
				batch.draw( bird2_2,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10 );
				batch.draw( bird2_3,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10 );

				enemyCircle[i] = new Circle( enemyx[i] +Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset[i] +Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
				enemyCircle2[i] = new Circle( enemyx[i] +Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset2[i] +Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
				enemyCircle3[i] = new Circle( enemyx[i] +Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset3[i] +Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			}





			if ( birdY >0 ){  
				velocity = velocity +gravity;
				birdY = birdY-velocity;
			}else {
				gameState =2;
			}

		}else if ( gameState ==0 ){
			if ( Gdx.input.justTouched() ){
				gameState = 1;

			}

		} else if ( gameState ==2 ){
			font2.draw( batch,"Game Over ! Tap To Play Again", 100,Gdx.graphics.getHeight()/2);

			if ( Gdx.input.justTouched() ){
				gameState=1;

				birdY = Gdx.graphics.getHeight() /3;

				for(int i=0 ; i<numberOfEnemies ; i++) {


					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyx[i] = Gdx.graphics.getWidth() - bird2.getWidth() / 2 + i * distance;


					enemyCircle[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();

				}
				velocity =0;
				scoredEnemy =0;
				score=0;
			}
		}


		batch.draw( bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10 );

		font.draw( batch,String.valueOf( score ),100,200 );

		batch.end();

		birdCircle.set( birdX+ Gdx.graphics.getWidth()/30,birdY+ Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor( Color.BLACK );
		//shapeRenderer.circle( birdCircle.x,birdCircle.y,birdCircle.radius );



		for (int i =0 ; i<numberOfEnemies; i++){
			//shapeRenderer.circle( enemyx[i] +Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset[i] +Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30 );
			//shapeRenderer.circle( enemyx[i] +Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset2[i] +Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30 );
			//shapeRenderer.circle( enemyx[i] +Gdx.graphics.getWidth()/30 , Gdx.graphics.getHeight()/2 + enemyOffset3[i] +Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30 );


			if ( Intersector.overlaps( birdCircle,enemyCircle[i] ) || Intersector.overlaps( birdCircle,enemyCircle2[i] ) || Intersector.overlaps( birdCircle,enemyCircle3[i] ) ){
				gameState = 2;

			}


		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
}
