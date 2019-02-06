java_library(
    name = "grpc_context",
    exports = [
        "//external:jar/io/grpc/grpc_context"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



java_library(
    name = "grpc_core",
    exports = [
        "//3rdparty/jvm/com/google/code/findbugs:jsr305",
        "//3rdparty/jvm/com/google/code/gson:gson",
        "//3rdparty/jvm/com/google/errorprone:error_prone_annotations",
        "//3rdparty/jvm/com/google/guava:guava",
        "//3rdparty/jvm/io/opencensus:opencensus_api",
        "//3rdparty/jvm/io/opencensus:opencensus_contrib_grpc_metrics",
        "//3rdparty/jvm/org/checkerframework:checker_compat_qual",
        "//3rdparty/jvm/org/codehaus/mojo:animal_sniffer_annotations",
        "//external:jar/io/grpc/grpc_core",
        ":grpc_context"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



java_library(
    name = "grpc_netty_shaded",
    exports = [
        "//external:jar/io/grpc/grpc_netty_shaded",
        ":grpc_core"
    ],
    visibility = [
        "//visibility:public"
    ]
)



java_library(
    name = "grpc_protobuf",
    exports = [
        "//3rdparty/jvm/com/google/api/grpc:proto_google_common_protos",
        "//3rdparty/jvm/com/google/guava:guava",
        "//3rdparty/jvm/com/google/protobuf:protobuf_java",
        "//3rdparty/jvm/org/checkerframework:checker_compat_qual",
        "//external:jar/io/grpc/grpc_protobuf",
        ":grpc_core",
        ":grpc_protobuf_lite"
    ],
    visibility = [
        "//visibility:public"
    ]
)



java_library(
    name = "grpc_protobuf_lite",
    exports = [
        "//3rdparty/jvm/com/google/guava:guava",
        "//3rdparty/jvm/org/checkerframework:checker_compat_qual",
        "//external:jar/io/grpc/grpc_protobuf_lite",
        ":grpc_core"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



java_library(
    name = "grpc_stub",
    exports = [
        "//external:jar/io/grpc/grpc_stub",
        ":grpc_core"
    ],
    visibility = [
        "//visibility:public"
    ]
)


