load("@build_stack_rules_proto//java:java_grpc_library.bzl", "java_grpc_library")

java_binary(
    name = "server",
    srcs = glob([
        "src/main/java/**/*.java",
        ]),
    resources = glob(["src/main/resources/**"]),
    main_class = "edu.jyo.app.ApplicationServer",
    deps = [
        "//3rdparty/jvm/io/grpc:grpc_stub",
        "//3rdparty/jvm/io/grpc:grpc_netty_shaded",
        "//3rdparty/jvm/io/grpc:grpc_protobuf",
        ":all_java_grpc",
    ],
    runtime_deps = [

    ]
)

java_binary(
    name = "greet_client",
    srcs = glob([
        "src/main/java/**/*.java",
        ]),
    resources = glob(["src/main/resources/**"]),
    main_class = "edu.jyo.app.gui.GreeterClient",
    deps = [
        "//3rdparty/jvm/io/grpc:grpc_stub",
        "//3rdparty/jvm/io/grpc:grpc_netty_shaded",
        "//3rdparty/jvm/io/grpc:grpc_protobuf",
        ":all_java_grpc",
    ],
    runtime_deps = [

    ]
)

proto_library(
    name = "all_proto",
    srcs = glob([
            "src/main/proto/**/*.proto"]),
    deps = [
        #"@com_google_protobuf//:any_proto",
        #"@com_google_protobuf//:empty_proto"    #uncomment to use `import "google/protobuf/empty.proto";`
            ],
)

java_proto_library(
    name = "all_java_proto",
    deps = [":all_proto"],
)

java_grpc_library(
    name = "all_java_grpc",
    srcs = glob([
        "src/main/java/**/*.java",
        ]),
    deps = [":all_proto"],
)