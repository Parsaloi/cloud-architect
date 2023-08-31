
## Practical Lab Sessions with the Service Meshes

### Case Study: Linkerd

```bash
linkerd version
Client version: stable-2.14.0
Server version: unavailable
```

```bash
linkerd install --crds | kubectl apply -f -
Rendering Linkerd CRDs...
Next, run `linkerd install | kubectl apply -f -` to install the control plane.

customresourcedefinition.apiextensions.k8s.io/authorizationpolicies.policy.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/httproutes.policy.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/meshtlsauthentications.policy.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/networkauthentications.policy.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/serverauthorizations.policy.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/servers.policy.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/serviceprofiles.linkerd.io created
customresourcedefinition.apiextensions.k8s.io/httproutes.gateway.networking.k8s.io created
```

```bash
linkerd install | kubectl apply -f -
Can't install the Linkerd control plane in the 'linkerd' namespace. Reason: ConfigMap/linkerd-config already exists.
Run the command `linkerd upgrade`, if you are looking to upgrade Linkerd.
error: no objects passed to apply
```

```bash
kubectl get pods -n linkerd
NAME                                     READY   STATUS            RESTARTS   AGE
linkerd-destination-8877c87-8dgnc        0/4     PodInitializing   0          63s
linkerd-identity-7b7c7b87b5-h4cx5        0/2     PodInitializing   0          63s
linkerd-proxy-injector-cb7d77df6-dk6xh   0/2     PodInitializing   0          63s
```

```linkerd check
kubernetes-api
--------------
√ can initialize the client
√ can query the Kubernetes API

kubernetes-version
------------------
√ is running the minimum Kubernetes API version

linkerd-existence
-----------------
√ 'linkerd-config' config map exists
√ heartbeat ServiceAccount exist
√ control plane replica sets are ready
√ no unschedulable pods
√ control plane pods are ready
√ cluster networks contains all node podCIDRs
√ cluster networks contains all pods
√ cluster networks contains all services

linkerd-config
--------------
√ control plane Namespace exists
√ control plane ClusterRoles exist
√ control plane ClusterRoleBindings exist
√ control plane ServiceAccounts exist
√ control plane CustomResourceDefinitions exist
√ control plane MutatingWebhookConfigurations exist
√ control plane ValidatingWebhookConfigurations exist
√ proxy-init container runs as root user if docker container runtime is used

linkerd-identity
----------------
√ certificate config is valid
√ trust anchors are using supported crypto algorithm
√ trust anchors are within their validity period
√ trust anchors are valid for at least 60 days
√ issuer cert is using supported crypto algorithm
√ issuer cert is within its validity period
√ issuer cert is valid for at least 60 days
√ issuer cert is issued by the trust anchor

linkerd-webhooks-and-apisvc-tls
-------------------------------
√ proxy-injector webhook has valid cert
√ proxy-injector cert is valid for at least 60 days
√ sp-validator webhook has valid cert
√ sp-validator cert is valid for at least 60 days
√ policy-validator webhook has valid cert
√ policy-validator cert is valid for at least 60 days

linkerd-version
---------------
√ can determine the latest version
√ cli is up-to-date

control-plane-version
---------------------
√ can retrieve the control plane version
√ control plane is up-to-date
√ control plane and cli versions match

linkerd-control-plane-proxy
---------------------------
√ control plane proxies are healthy
√ control plane proxies are up-to-date
√ control plane proxies and cli versions match

Status check results are √
```

```bash
kubectl get pods -n linkerd
NAME                                     READY   STATUS    RESTARTS   AGE
linkerd-destination-8877c87-8dgnc        4/4     Running   0          3m36s
linkerd-identity-7b7c7b87b5-h4cx5        2/2     Running   0          3m36s
linkerd-proxy-injector-cb7d77df6-dk6xh   2/2     Running   0          3m36s
```

## Testing Demo App Service

```bash
kubectl get pods -n emojivoto
NAME                        READY   STATUS              RESTARTS   AGE
emoji-68cdd48fc7-zdl9x      0/1     ContainerCreating   0          19s
vote-bot-85c88b944d-q45n9   0/1     ContainerCreating   0          19s
voting-5b7f854444-7v8dk     0/1     ContainerCreating   0          19s
web-679ccff67b-884sr        0/1     ContainerCreating   0          18s
```

```bash
kubectl get pods -n emojivoto
NAME                        READY   STATUS    RESTARTS   AGE
emoji-68cdd48fc7-zdl9x      1/1     Running   0          10m
vote-bot-85c88b944d-q45n9   1/1     Running   0          10m
voting-5b7f854444-7v8dk     1/1     Running   0          10m
web-679ccff67b-884sr        1/1     Running   0          10m
```

```bash
kubectl -n emojivoto port-forward svc/web-svc 8080:80
Forwarding from 127.0.0.1:8080 -> 8080
Forwarding from [::1]:8080 -> 8080
Handling connection for 8080

# the next commands breaks this
E0831 07:27:02.498044   29288 portforward.go:409] an error occurred forwarding 8080 -> 8080: error forwarding port 8080 to pod 7fcd9b13a8f2532fdf2161179305f07fe1d63dbf66d973485460e19688eb97a1, uid : failed to find sandbox "7fcd9b13a8f2532fdf2161179305f07fe1d63dbf66d973485460e19688eb97a1" in store: not found
error: lost connection to pod
```

```bash
kubectl get -n emojivoto deploy -o yaml \
| linkerd inject - \
| kubectl apply -f -
deployment "emoji" injected
deployment "vote-bot" injected
deployment "voting" injected
deployment "web" injected

deployment.apps/emoji configured
deployment.apps/vote-bot configured
deployment.apps/voting configured
deployment.apps/web configured
```
The above command retrieves all of the deployments running in the `emojivoto` namespace, runs their manifest through `linkerd inject`, and then reapplies it to the  
cluster. (The `linkerd inject` command simply adds annotations to the pod spec that instruct Linkerd to inject the proxy into the pods when they are created)

As with `install`, `inject` is a pure text operation, meaninng that you can inspect the input and output before you use it. Once piped into `kubectl apply`,  
Kubernetes will execute a rolling update and updates each pod with the data plane's proxies

```bash
linkerd -n emojivoto check --proxy
kubernetes-api
--------------
√ can initialize the client
√ can query the Kubernetes API

kubernetes-version
------------------
√ is running the minimum Kubernetes API version

linkerd-existence
-----------------
√ 'linkerd-config' config map exists
√ heartbeat ServiceAccount exist
√ control plane replica sets are ready
√ no unschedulable pods
√ control plane pods are ready
√ cluster networks contains all node podCIDRs
√ cluster networks contains all pods
√ cluster networks contains all services

linkerd-config
--------------
√ control plane Namespace exists
√ control plane ClusterRoles exist
√ control plane ClusterRoleBindings exist
√ control plane ServiceAccounts exist
√ control plane CustomResourceDefinitions exist
√ control plane MutatingWebhookConfigurations exist
√ control plane ValidatingWebhookConfigurations exist
√ proxy-init container runs as root user if docker container runtime is used

linkerd-identity
----------------
√ certificate config is valid
√ trust anchors are using supported crypto algorithm
√ trust anchors are within their validity period
√ trust anchors are valid for at least 60 days
√ issuer cert is using supported crypto algorithm
√ issuer cert is within its validity period
√ issuer cert is valid for at least 60 days
√ issuer cert is issued by the trust anchor

linkerd-webhooks-and-apisvc-tls
-------------------------------
√ proxy-injector webhook has valid cert
√ proxy-injector cert is valid for at least 60 days
√ sp-validator webhook has valid cert
√ sp-validator cert is valid for at least 60 days
√ policy-validator webhook has valid cert
√ policy-validator cert is valid for at least 60 days

linkerd-identity-data-plane
---------------------------
√ data plane proxies certificate match CA

linkerd-version
---------------
√ can determine the latest version
√ cli is up-to-date

linkerd-control-plane-proxy
---------------------------
√ control plane proxies are healthy
√ control plane proxies are up-to-date
√ control plane proxies and cli versions match

linkerd-data-plane
------------------
√ data plane namespace exists
√ data plane proxies are ready
√ data plane is up-to-date
√ data plane and cli versions match
√ data plane pod labels are configured correctly
√ data plane service labels are configured correctly
√ data plane service annotations are configured correctly
√ opaque ports are properly annotated

Status check results are √
```

```bash
# install the on-cluster metrics stack
linkerd viz install | kubectl apply -f -
namespace/linkerd-viz created
clusterrole.rbac.authorization.k8s.io/linkerd-linkerd-viz-metrics-api created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-metrics-api created
serviceaccount/metrics-api created
clusterrole.rbac.authorization.k8s.io/linkerd-linkerd-viz-prometheus created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-prometheus created
serviceaccount/prometheus created
clusterrole.rbac.authorization.k8s.io/linkerd-linkerd-viz-tap created
clusterrole.rbac.authorization.k8s.io/linkerd-linkerd-viz-tap-admin created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-tap created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-tap-auth-delegator created
serviceaccount/tap created
rolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-tap-auth-reader created
secret/tap-k8s-tls created
apiservice.apiregistration.k8s.io/v1alpha1.tap.linkerd.io created
role.rbac.authorization.k8s.io/web created
rolebinding.rbac.authorization.k8s.io/web created
clusterrole.rbac.authorization.k8s.io/linkerd-linkerd-viz-web-check created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-web-check created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-web-admin created
clusterrole.rbac.authorization.k8s.io/linkerd-linkerd-viz-web-api created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-linkerd-viz-web-api created
serviceaccount/web created
service/metrics-api created
deployment.apps/metrics-api created
server.policy.linkerd.io/metrics-api created
authorizationpolicy.policy.linkerd.io/metrics-api created
meshtlsauthentication.policy.linkerd.io/metrics-api-web created
networkauthentication.policy.linkerd.io/kubelet created
configmap/prometheus-config created
service/prometheus created
deployment.apps/prometheus created
server.policy.linkerd.io/prometheus-admin created
authorizationpolicy.policy.linkerd.io/prometheus-admin created
service/tap created
deployment.apps/tap created
server.policy.linkerd.io/tap-api created
authorizationpolicy.policy.linkerd.io/tap created
clusterrole.rbac.authorization.k8s.io/linkerd-tap-injector created
clusterrolebinding.rbac.authorization.k8s.io/linkerd-tap-injector created
serviceaccount/tap-injector created
secret/tap-injector-k8s-tls created
mutatingwebhookconfiguration.admissionregistration.k8s.io/linkerd-tap-injector-webhook-config created
service/tap-injector created
deployment.apps/tap-injector created
server.policy.linkerd.io/tap-injector-webhook created
authorizationpolicy.policy.linkerd.io/tap-injector created
networkauthentication.policy.linkerd.io/kube-api-server created
service/web created
deployment.apps/web created
serviceprofile.linkerd.io/metrics-api.linkerd-viz.svc.cluster.local created
serviceprofile.linkerd.io/prometheus.linkerd-viz.svc.cluster.local created
```

```bash
linkerd check
kubernetes-api
--------------
√ can initialize the client
√ can query the Kubernetes API

kubernetes-version
------------------
√ is running the minimum Kubernetes API version

linkerd-existence
-----------------
√ 'linkerd-config' config map exists
√ heartbeat ServiceAccount exist
√ control plane replica sets are ready
√ no unschedulable pods
√ control plane pods are ready
√ cluster networks contains all node podCIDRs
√ cluster networks contains all pods
√ cluster networks contains all services

linkerd-config
--------------
√ control plane Namespace exists
√ control plane ClusterRoles exist
√ control plane ClusterRoleBindings exist
√ control plane ServiceAccounts exist
√ control plane CustomResourceDefinitions exist
√ control plane MutatingWebhookConfigurations exist
√ control plane ValidatingWebhookConfigurations exist
√ proxy-init container runs as root user if docker container runtime is used

linkerd-identity
----------------
√ certificate config is valid
√ trust anchors are using supported crypto algorithm
√ trust anchors are within their validity period
√ trust anchors are valid for at least 60 days
√ issuer cert is using supported crypto algorithm
√ issuer cert is within its validity period
√ issuer cert is valid for at least 60 days
√ issuer cert is issued by the trust anchor

linkerd-webhooks-and-apisvc-tls
-------------------------------
√ proxy-injector webhook has valid cert
√ proxy-injector cert is valid for at least 60 days
√ sp-validator webhook has valid cert
√ sp-validator cert is valid for at least 60 days
√ policy-validator webhook has valid cert
√ policy-validator cert is valid for at least 60 days

linkerd-version
---------------
√ can determine the latest version
√ cli is up-to-date

control-plane-version
---------------------
√ can retrieve the control plane version
√ control plane is up-to-date
√ control plane and cli versions match

linkerd-control-plane-proxy
---------------------------
√ control plane proxies are healthy
√ control plane proxies are up-to-date
√ control plane proxies and cli versions match

Running viz extension check...
```

```bash
linkerd-viz
-----------
√ linkerd-viz Namespace exists
√ can initialize the client
√ linkerd-viz ClusterRoles exist
√ linkerd-viz ClusterRoleBindings exist
√ tap API server has valid cert
√ tap API server cert is valid for at least 60 days
√ tap API service is running
√ linkerd-viz pods are injected
√ viz extension pods are running
√ viz extension proxies are healthy
√ viz extension proxies are up-to-date
√ viz extension proxies and cli versions match
√ prometheus is installed and configured correctly
√ viz extension self-check

Status check results are √
```

```bash
linkerd viz dashboard &
[1] 33688
```

```bash
Linkerd dashboard available at:
http://localhost:50750
Grafana dashboard available at:
http://localhost:50750/grafana
Opening Linkerd dashboard in the default browser
```

## What's Next?

- Learn how to use Linkerd to ***debug the erros in Emojivoto***  
<https://linkerd.io/2.14/debugging-an-app/>
- Learn how to ***add your own services*** to Linerd without downtime  
<https://linkerd.io/2.14/adding-your-service/>
- Learn how to install other ***Linkerd extensions*** such as `Jaeger` and the **multicluster extension**  
<https://linkerd.io/2.14/tasks/extensions/> <>
- Leanr how to setup ***automatic control plane mTLS credential rotation*** for long-lived clusters  
<https://linkerd.io/2.14/tasks/automatically-rotating-control-plane-tls-credentials/>
- Learn hot to ***restrict access to services using authorization policy***  
<https://linkerd.io/2.14/tasks/restricting-access/>
- Hop into the **#linked** channel on the <> and say hi!  
<https://slack.linkerd.io/>

ABove all else: welcome to the Linkerd community! 😜
