import io.kubernetes.client.*;
import io.kubernetes.client.apis.*;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.*;

public class KubernetesClientExample {
	
	public static void main(String[] args) throws Exception {
		
		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);
	}
}
