
```bash
cd <my-native-image-app-directory>
```

```bash
mvn package -P native-image
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/software/amazon/awssdk/bom/2.16.1/bom-2.16.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/software/amazon/awssdk/bom/2.16.1/bom-2.16.1.pom (63 kB at 30 kB/s)
[INFO] 
[INFO] -----------< com.example.mynativeimageapp:mynativeimageapp >------------
[INFO] Building mynativeimageapp 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-compiler-plugin/3.6.1/maven-compiler-plugin-3.6.1.pom
...

========================================================================================================================
GraalVM Native Image: Generating 'mynativeimageapp' (executable)...
========================================================================================================================
[1/8] Initializing...                                                                                   (13.8s @ 0.11GB)
 Java version: 17.0.8+7, vendor version: GraalVM CE 17.0.8+7.1
 Graal compiler: optimization level: 2, target machine: x86-64-v3
 C compiler: gcc (pc, x86_64, 13.2.1)
 Garbage collector: Serial GC (max heap size: 80% of RAM)
[2/8] Performing analysis...  [******]                                                                 (120.3s @ 0.84GB)
   7,948 (82.85%) of  9,593 types reachable
  12,006 (60.91%) of 19,711 fields reachable
  38,346 (55.40%) of 69,222 methods reachable
   2,437 types,    95 fields, and 1,680 methods registered for reflection
      62 types,    67 fields, and    55 methods registered for JNI access
       4 native libraries: dl, pthread, rt, z
[3/8] Building universe...
[4/8] Parsing methods...      [****]                                                                    (12.4s @ 0.75GB)
[5/8] Inlining methods...     [***]                                                                      (6.8s @ 0.95GB)
[6/8] Compiling methods...    [**********]                                                              (96.2s @ 0.83GB)
[7/8] Layouting methods...    [***]                                                                      (8.9s @ 0.78GB)
[8/8] Creating image...       [***]                                                                     (10.5s @ 1.22GB)
  18.11MB (36.10%) for code area:    24,294 compilation units
  30.64MB (61.10%) for image heap:  247,216 objects and 2,995 resources
   1.41MB ( 2.80%) for other data
  50.16MB in total
------------------------------------------------------------------------------------------------------------------------
Top 10 origins of code area:                                Top 10 object types in image heap:
  10.93MB java.base                                           12.01MB byte[] for embedded resources
   1.78MB jackson-databind-2.12.1.jar                          3.88MB byte[] for code metadata
   1.62MB regions-2.16.1.jar                                   2.12MB java.lang.String
   1.06MB svm.jar (Native Image)                               1.98MB byte[] for general heap data
 427.65kB jackson-core-2.12.1.jar                              1.85MB java.lang.Class
 351.04kB java.rmi                                             1.85MB byte[] for java.lang.String
 337.83kB jdk.crypto.ec                                      683.03kB com.oracle.svm.core.hub.DynamicHubCompanion
 204.33kB java.naming                                        608.42kB java.lang.Object[]
 133.08kB jdk.naming.dns                                     516.94kB java.util.HashMap$Node
 121.17kB java.logging                                       403.63kB byte[] for reflection metadata
   1.01MB for 31 more packages                                 4.18MB for 1996 more object types
------------------------------------------------------------------------------------------------------------------------
Recommendations:
 HEAP: Set max heap for improved and more predictable memory usage.
 CPU:  Enable more CPU features with '-march=native' for improved performance.
------------------------------------------------------------------------------------------------------------------------
                        21.7s (7.5% of total time) in 93 GCs | Peak RSS: 2.32GB | CPU load: 6.69
------------------------------------------------------------------------------------------------------------------------
Produced artifacts:
 /home/parsa/Documents/arch-parsa/Development/mini-projects/cloud-architect/manage/Cloud_Providers/AWS/maven_aws_workflows/mynativeimageapp/target/mynativeimageapp (executable)
========================================================================================================================
Finished generating 'mynativeimageapp' in 4m 46s.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  06:33 min
[INFO] Finished at: 2023-08-31T17:05:22+03:00
[INFO] ------------------------------------------------------------------------
```
