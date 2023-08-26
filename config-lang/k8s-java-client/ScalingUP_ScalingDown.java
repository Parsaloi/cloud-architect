package arch.parsa.scaling;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppV1Api;
import io.kubernetes.client.models.V1Scale;
import io.kubernetes.client.models.V1ScaleSpec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class ScalingUp_ScalingDown extends AbstractVerticle{

	public static void main(String [] args) {
		Launcher.executeCommand("run", ScalingUp_ScalingDown.class.getName())
	}

	@Override
	private void start() throws Exception {
		AppsV1Api api = new AppsV1Api();

		// Scale up the deployment
		V1Scale scale = new V1Scale();
		scale.setApiVersion("apps/v1");
		scale.setKind("Scale");
		scale.setMetadata(deployment.getMetadata()); // Assuming the deployment already exists in the cluster

		V1ScaleSpec spec = new V1ScaleSpec();
		spec.setReplicas(5);
		scale.setSpec(spec);

		api.replaceNamespaceDeploymentScale("my-deployment", "default", scale, null, null);

		// Scale down the deployment
		spec.setReplicas(3);
		api.replaceNameSpacedDeploymentScale("my-deployment", "default", scale, null, null);
	}
}
