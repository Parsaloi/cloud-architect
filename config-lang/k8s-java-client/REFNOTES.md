
## Intro to the Kubernetes Java Client
> <https://collabnix.com/a-quick-intro-to-the-kubernetes-java-client/>  
> > Ajeet Raina

**Kubernetes** is a popular *container orchestration system* that ***simplifies the deployment, scaling and management of containerized applications***.  

The **Kubernetes Java Client** is a powerful library that allows Java developers to interact with Kubernetes clusters *programmatically*, and perform  
a wide range of operations, including:  
- managing pods,  
- services,  
- deployments,  
- and more :)

### What is the Kubernetes Java Client?

The **Kubernetes Java Client** is a Java library that provides a programmatic interface to interact with a Kubernetes cluster. It is a *client-side*  
library that can be used to perform various operations on Kubernetes resources, such as *pods, services, deployments, and more*

The `Kubernetes Java Client` is built on top of the Kubernetes REST API, which means that it interacts with Kubernetes resources using the same API  
that is used by the `kubectl` CLI tool and other Kubernetes clients. This ensures that the `Kubernetes Java Client` is always up-to-date with the latest  
Kubernetes features and changes

The `Kubernetes Java Client` provides a simple and intuitive API that abstracts away the complexity of interacting with Kubernetes resources. It  
provides a wide range of functionality, including:  

- Creating, updating, and deleting Kubernetes resources
- Scaling up and down deployments
- Retrieving and watching resource events
- Executing commands inside containers :)

### How to use the Kubernetes Java Client

To use the Kubernetes Java Client, I will need to add it as a dependency to my Java project. The Kubernetes Java Client is available on Maven Central,  
so you can add it to your project by adding the following dependency to your `pom.xml` file:  

```maven
<dependency>
    <groupId>io.kubernetes</groupId>
    <artifactId>client-java</artifactId>
    <version>10.0.0</version>
</dependency>
```

Once I have added the Kubernetes Java Client to my project, I can start using it to interact with a Kubernetes cluster. The first step is to create a  
`Kubernetes client object`:  

```java
import io.kubernetes.client.*;
import io.kubernetes.client.apis.*;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.*;

public class KubernetesClientExample {
    
    // Use other more sophisticated launchers on top of this classic
    public static void main(String[] args) throws Exception {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
    }
}
```

In this example, we are using the `Config.defaultClient()` method to create a default Kubernetes client object. This method reads the Kubernetes  
configuration file located at `~/.kube/config`, and uses the credentials and settings defined in the file to create a client object

Once you have a client object, you can use it to create instances of Kubernetes API classes, such as `CoreV1Api` for interacting with the core k8s  
resources:  

```java
CoreV1Api api = new CoreV1Api();
```

With the `CoreV1Api` object, you can interact with Kubernetes resources, such as *pods, services, and namespaces*. For example, to list all pods in a  
Kubernetes cluster, you can use the following code:  

```java
V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null;
    for (V1Pod item : podList.getItems()) {
        System.out.println(item.getMetadata().getName());
    }
// I prefer using the iterator interface X lambda expressions anyday over traditional loops
```

This code retrieve a list of all pods in the cluster and prints their names to the console

## Creating, Updating, and Deleting K8s resources..

## Scaling Up and Down Deployments:


