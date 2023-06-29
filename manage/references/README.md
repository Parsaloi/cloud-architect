
# Service ClusterIP allocation

In Kubernetes, **Services** are an abstract way to expose an application running on a set of *Pods*. Services can have a ***cluster-scoped*** *virtual IP address*  
(using a Service of `type: clusterIP`). Clients can connect using that virtual IP address, and *Kubernetes* then load-balances traffic to that Service across the  
different *backing Pods*.

## How Service ClusterIPs are allocated

When *Kubernetes* needs to assign a virtual IP address for a Service, that assignment happens one of two ways:  
- ***dynamically***  
the cluster's control plane automatically picks a free IP address from within the configured IP range for `type: ClusterIP` Services.
- ***statically***  
you specify an IP address of your choice, from within the configured IP range for Services

Across your whole cluster, every Service `Cluster IP` must be unique. Trying to create a Service with a specific `ClusterIP` that has already been allocated will  
return an error :)

## Why do you need to reserve Service Cluster IPs

Sometimes you may want to have Services running in well-known IP addresses, so other components and users in the cluster can use them. The best example  
id the *DNS* Service for the cluster. Assuming you configured your cluster with Service IP range 10.96.0.0/16 and you want your DNS Service IP to be 10.96.0.10,  
you'd have to create a Service like so...but as it was explained before, the IP address 10.96.0.10 has not been reserved; if other Services are create before or in  
parallel with *dynamic allocation*, there is a chance they can allocate this IP, hence, you will not be able to create the DNS Service because it will fail with a  
*conficlt error*.


