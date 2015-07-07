using UnityEngine;
using System.Collections;

public class CameraController : MonoBehaviour {
	// TODO change to enum

	public enum CameraMode {
		CAMERA_MODE_EYE,
		CAMERA_MODE_FLAT,
		CAMERA_MODE_PLAYER
	}

	private static Vector3 FLAT_POSITION = new Vector3 (0, 20, 0);
	private static Quaternion FLAT_ROTATION = Quaternion.Euler(90, 0, 0);

	private static int ZOOM_MAX = 10;
	private static int ZOOM_MIN = -10;
	private int zoom = 0;


	public static GameObject player = null;
	private static GameObject playerEye;


	private CameraMode currentMode;

	// Use this for initialization
	void Start () {
		currentMode = CameraMode.CAMERA_MODE_FLAT;
		transform.position = FLAT_POSITION;
	}

	void LateUpdate() {
		/*Ray ray;
		RaycastHit hit;

		ray = Camera.main.ScreenPointToRay(Input.mousePosition);
		if(Physics.Raycast(ray, out hit))
		{
			print (hit.collider.name);
		}*/
		
		switch (currentMode) {
		case CameraMode.CAMERA_MODE_EYE:
			if (player != null) {
				playerEye = player.transform.Find("RatEye").gameObject;
				
				transform.position = playerEye.transform.position + new Vector3(-2, 2 + zoom, -2);
				transform.LookAt(playerEye.transform.position);
			}
			
			break;
		case CameraMode.CAMERA_MODE_FLAT:
			transform.position = FLAT_POSITION + new Vector3(0, zoom, 0);
			transform.rotation = FLAT_ROTATION;
			
			break;
		case CameraMode.CAMERA_MODE_PLAYER:
			transform.position = player.transform.position + new Vector3(-2, 2 + zoom, -2);
			transform.LookAt(player.transform.position);

			// rotation
			break;
		}
	}

	public void setCameraMode(CameraMode cameraMode) {
		currentMode = cameraMode;
	}

	public GameObject Player {
		get {
			return player;
		}
		set {
			player = value;
		}
	}

	public void onCameraButtonClicked() {
		if (currentMode == CameraMode.CAMERA_MODE_FLAT) {
			setCameraMode (CameraMode.CAMERA_MODE_PLAYER);
		} /*else if (currentMode == CameraMode.CAMERA_MODE_PLAYER) {
			setCameraMode (CameraMode.CAMERA_MODE_EYE);
		} */else {
			setCameraMode (CameraMode.CAMERA_MODE_FLAT);
		}
	}

	public void onZoomInButtonClicked() {
		zoom -= 1;
		if (zoom < ZOOM_MIN)
			zoom = ZOOM_MIN;
	}

	public void onZoomOutButtonClicked() {
		zoom += 1;
		if (zoom > ZOOM_MAX)
			zoom = ZOOM_MAX;
	}
}
