package dev.jabo.kree;

import java.awt.Graphics;

public class GameObject {

	public Transform transform = new Transform();
	
	private Component[] components = new Component[0];
	
	public float id;
	public String name;
	
	private Scene parentScene;
	
	public GameObject(Scene parentScene, String name) {
		this.name = name;
		this.parentScene = parentScene;
		
		// Add to Scene GameObjects
		GameObject[] newGameObjectList = new GameObject[parentScene.gameObjects.length + 1];
		for(int i = 0; i < parentScene.gameObjects.length; i++) {
			newGameObjectList[i] = parentScene.gameObjects[i];
		}
		newGameObjectList[parentScene.gameObjects.length] = this;
		parentScene.gameObjects = newGameObjectList;
		// End
		
		this.id = (float) (Math.random() * 100);
	}
	
	public void Update() {
		for(int i = 0; i < components.length; i++) {
			if(!components[i].disabled) {
				components[i].Update();
			}
		}
	}
	
	public void Render(Graphics g) {
		for(int i = 0; i < components.length; i++) {
			if(!components[i].disabled) {
				components[i].Render(g);
			}
		}		
	}
	
	public Component getComponent(String componentName) {
		for(int i = 0; i < components.length; i++) {
			if(components[i].name == componentName) {
				return components[i];
			}
		}
		return null;
	}
	
	public void addComponent(Component component) {
		
		component.gameObject = this;
		
		// Add to GameObject's Component
		Component[] newComponentList = new Component[components.length + 1];
		for(int i = 0; i < components.length; i++) {
			newComponentList[i] = components[i];
		}
		newComponentList[components.length] = component;
		components = newComponentList;
		// End
	}
	
	// Set GameObject's position
	public void setPosition(Vector2 position) {
		transform.position = position;
	}
	
	// Set GameObject's dimension
	public void setScale(Vector2 scale) {
		transform.scale = scale;
	}
	
	public void destroy() {
		for(int i = 0; i < parentScene.gameObjects.length; i++) {
			if(parentScene.gameObjects[i].id == this.id) {
				parentScene.gameObjects[i] = null;
				
				// Remove GameObject from Scene
				GameObject[] newGameObjectList = new GameObject[parentScene.gameObjects.length - 1];
				int c = 0;
				for(int j = 0; j < parentScene.gameObjects.length; j++) {
					if(parentScene.gameObjects[j] != null) {
						newGameObjectList[c] = parentScene.gameObjects[j];
						c++;
					}
				}
				parentScene.gameObjects = newGameObjectList;
				// End
				
				return;
			}
		}
	}
	
	public Scene getParentScene() {
		return this.parentScene;
	}
	
}
