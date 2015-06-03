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
	private static int ZOOM_MIN = 1;

	public static GameObject player = null;
	
	private CameraMode currentMode;

	// Use this for initialization
	void Start () {
		currentMode = CameraMode.CAMERA_MODE_FLAT;
		transform.position = FLAT_POSITION;
	}
	
	// Update is called once per frame
	void Update () {

		if (Input.GetButtonDown ("Camera Eye") && player != null) {
			setCameraMode(CameraMode.CAMERA_MODE_EYE);
		} else if (Input.GetButtonDown ("Camera Flat")) {
			setCameraMode(CameraMode.CAMERA_MODE_FLAT);
		}

		// Work with zooming

		if (Input.GetButtonDown("Zoom Out") && transform.position.y < ZOOM_MAX) {
			Debug.Log ("Zoom Out");
			Vector3 newPosition = transform.position + new Vector3(0, 1, 0);
			transform.position = Vector3.Lerp(transform.position, newPosition, 1);
		} 

		if (Input.GetKeyDown(KeyCode.Equals)  && transform.position.y > ZOOM_MIN) {
			Debug.Log ("Zoom In");
			Vector3 newPosition = transform.position - new Vector3(0, 1, 0);
			transform.position = Vector3.Lerp(transform.position, newPosition, 1);
		}

		switch (currentMode) {
		case CameraMode.CAMERA_MODE_EYE:
			if (player != null) {
				GameObject playerEye = player.transform.Find("PlayerEye").gameObject;
				
				transform.position = playerEye.transform.position;
				transform.rotation = playerEye.transform.rotation;
			}
			
			break;
		case CameraMode.CAMERA_MODE_FLAT:
			// transform.position = FLAT_POSITION;
			transform.rotation = FLAT_ROTATION;
			
			break;
		case CameraMode.CAMERA_MODE_PLAYER:
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
}
