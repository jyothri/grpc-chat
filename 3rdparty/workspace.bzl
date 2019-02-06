# Do not edit. bazel-deps autogenerates this file from dependencies.yml.
def _jar_artifact_impl(ctx):
    jar_name = "%s.jar" % ctx.name
    ctx.download(
        output=ctx.path("jar/%s" % jar_name),
        url=ctx.attr.urls,
        sha256=ctx.attr.sha256,
        executable=False
    )
    src_name="%s-sources.jar" % ctx.name
    srcjar_attr=""
    has_sources = len(ctx.attr.src_urls) != 0
    if has_sources:
        ctx.download(
            output=ctx.path("jar/%s" % src_name),
            url=ctx.attr.src_urls,
            sha256=ctx.attr.src_sha256,
            executable=False
        )
        srcjar_attr ='\n    srcjar = ":%s",' % src_name

    build_file_contents = """
package(default_visibility = ['//visibility:public'])
java_import(
    name = 'jar',
    tags = ['maven_coordinates={artifact}'],
    jars = ['{jar_name}'],{srcjar_attr}
)
filegroup(
    name = 'file',
    srcs = [
        '{jar_name}',
        '{src_name}'
    ],
    visibility = ['//visibility:public']
)\n""".format(artifact = ctx.attr.artifact, jar_name = jar_name, src_name = src_name, srcjar_attr = srcjar_attr)
    ctx.file(ctx.path("jar/BUILD"), build_file_contents, False)
    return None

jar_artifact = repository_rule(
    attrs = {
        "artifact": attr.string(mandatory = True),
        "sha256": attr.string(mandatory = True),
        "urls": attr.string_list(mandatory = True),
        "src_sha256": attr.string(mandatory = False, default=""),
        "src_urls": attr.string_list(mandatory = False, default=[]),
    },
    implementation = _jar_artifact_impl
)

def jar_artifact_callback(hash):
    src_urls = []
    src_sha256 = ""
    source=hash.get("source", None)
    if source != None:
        src_urls = [source["url"]]
        src_sha256 = source["sha256"]
    jar_artifact(
        artifact = hash["artifact"],
        name = hash["name"],
        urls = [hash["url"]],
        sha256 = hash["sha256"],
        src_urls = src_urls,
        src_sha256 = src_sha256
    )
    native.bind(name = hash["bind"], actual = hash["actual"])


def list_dependencies():
    return [
    {"artifact": "com.google.api.grpc:proto-google-common-protos:1.12.0", "lang": "java", "sha1": "1140cc74df039deb044ed0e320035e674dc13062", "sha256": "bd60cd7a423b00fb824c27bdd0293aaf4781be1daba6ed256311103fb4b84108", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/api/grpc/proto-google-common-protos/1.12.0/proto-google-common-protos-1.12.0.jar", "source": {"sha1": "af18069b1d8368ffc8dce50a919df75f039cf507", "sha256": "936fdc055855a956ef82afb1b408bd0bd5ea5d040fe6f6fc25c4955879db649a", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/api/grpc/proto-google-common-protos/1.12.0/proto-google-common-protos-1.12.0-sources.jar"} , "name": "com_google_api_grpc_proto_google_common_protos", "actual": "@com_google_api_grpc_proto_google_common_protos//jar", "bind": "jar/com/google/api/grpc/proto_google_common_protos"},
    {"artifact": "com.google.code.findbugs:jsr305:3.0.2", "lang": "java", "sha1": "25ea2e8b0c338a877313bd4672d3fe056ea78f0d", "sha256": "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar", "source": {"sha1": "b19b5927c2c25b6c70f093767041e641ae0b1b35", "sha256": "1c9e85e272d0708c6a591dc74828c71603053b48cc75ae83cce56912a2aa063b", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2-sources.jar"} , "name": "com_google_code_findbugs_jsr305", "actual": "@com_google_code_findbugs_jsr305//jar", "bind": "jar/com/google/code/findbugs/jsr305"},
    {"artifact": "com.google.code.gson:gson:2.7", "lang": "java", "sha1": "751f548c85fa49f330cecbb1875893f971b33c4e", "sha256": "2d43eb5ea9e133d2ee2405cc14f5ee08951b8361302fdd93494a3a997b508d32", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/code/gson/gson/2.7/gson-2.7.jar", "source": {"sha1": "bbb63ca253b483da8ee53a50374593923e3de2e2", "sha256": "2d3220d5d936f0a26258aa3b358160741a4557e046a001251e5799c2db0f0d74", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/code/gson/gson/2.7/gson-2.7-sources.jar"} , "name": "com_google_code_gson_gson", "actual": "@com_google_code_gson_gson//jar", "bind": "jar/com/google/code/gson/gson"},
    {"artifact": "com.google.errorprone:error_prone_annotations:2.2.0", "lang": "java", "sha1": "88e3c593e9b3586e1c6177f89267da6fc6986f0c", "sha256": "6ebd22ca1b9d8ec06d41de8d64e0596981d9607b42035f9ed374f9de271a481a", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/errorprone/error_prone_annotations/2.2.0/error_prone_annotations-2.2.0.jar", "source": {"sha1": "a8cd7823aa1dcd2fd6677c0c5988fdde9d1fb0a3", "sha256": "626adccd4894bee72c3f9a0384812240dcc1282fb37a87a3f6cb94924a089496", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/errorprone/error_prone_annotations/2.2.0/error_prone_annotations-2.2.0-sources.jar"} , "name": "com_google_errorprone_error_prone_annotations", "actual": "@com_google_errorprone_error_prone_annotations//jar", "bind": "jar/com/google/errorprone/error_prone_annotations"},
    {"artifact": "com.google.guava:guava:25.1-android", "lang": "java", "sha1": "bdaab946ca5ad20253502d873ba0c3313d141036", "sha256": "f7b8f8fed176b9cf6831b98cb07320d7fbe91d99b29999f752c3821dfe45bdc8", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/guava/guava/25.1-android/guava-25.1-android.jar", "source": {"sha1": "a1fe5380cc1f2979e434c3b6ff43d69328ab4686", "sha256": "532a15d4f1378517ef80b0b6b8ca06ccc2533ed1595c191b698fffbbbe341d77", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/guava/guava/25.1-android/guava-25.1-android-sources.jar"} , "name": "com_google_guava_guava", "actual": "@com_google_guava_guava//jar", "bind": "jar/com/google/guava/guava"},
    {"artifact": "com.google.j2objc:j2objc-annotations:1.1", "lang": "java", "sha1": "ed28ded51a8b1c6b112568def5f4b455e6809019", "sha256": "2994a7eb78f2710bd3d3bfb639b2c94e219cedac0d4d084d516e78c16dddecf6", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar", "source": {"sha1": "1efdf5b737b02f9b72ebdec4f72c37ec411302ff", "sha256": "2cd9022a77151d0b574887635cdfcdf3b78155b602abc89d7f8e62aba55cfb4f", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1-sources.jar"} , "name": "com_google_j2objc_j2objc_annotations", "actual": "@com_google_j2objc_j2objc_annotations//jar", "bind": "jar/com/google/j2objc/j2objc_annotations"},
    {"artifact": "com.google.protobuf:protobuf-java:3.5.1", "lang": "java", "sha1": "8c3492f7662fa1cbf8ca76a0f5eb1146f7725acd", "sha256": "b5e2d91812d183c9f053ffeebcbcda034d4de6679521940a19064714966c2cd4", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/protobuf/protobuf-java/3.5.1/protobuf-java-3.5.1.jar", "source": {"sha1": "7235a28a13938050e8cd5d9ed5133bebf7a4dca7", "sha256": "3be3115498d543851443bfa725c0c5b28140e363b3b7dec97f4028cd17040fa4", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/com/google/protobuf/protobuf-java/3.5.1/protobuf-java-3.5.1-sources.jar"} , "name": "com_google_protobuf_protobuf_java", "actual": "@com_google_protobuf_protobuf_java//jar", "bind": "jar/com/google/protobuf/protobuf_java"},
    {"artifact": "io.grpc:grpc-context:1.18.0", "lang": "java", "sha1": "c63e8b86af0fb16b5696480dc14f48e6eaa7193b", "sha256": "12bc83b9fa3aa7550d75c4515b8ae74f124ba14d3692a5ef4737a2e855cbca2f", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-context/1.18.0/grpc-context-1.18.0.jar", "source": {"sha1": "b9de26a0c63e994297ca8b36e21139aabc84fc01", "sha256": "006faf6a8355041f5f03a7602bf9dfeeb3d1aa838c17b022862c9aaf438ca640", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-context/1.18.0/grpc-context-1.18.0-sources.jar"} , "name": "io_grpc_grpc_context", "actual": "@io_grpc_grpc_context//jar", "bind": "jar/io/grpc/grpc_context"},
# duplicates in io.grpc:grpc-core promoted to 1.18.0
# - io.grpc:grpc-netty-shaded:1.18.0 wanted version [1.18.0]
# - io.grpc:grpc-protobuf-lite:1.18.0 wanted version 1.18.0
# - io.grpc:grpc-protobuf:1.18.0 wanted version 1.18.0
# - io.grpc:grpc-stub:1.18.0 wanted version 1.18.0
    {"artifact": "io.grpc:grpc-core:1.18.0", "lang": "java", "sha1": "e21b343bba2006bac31bb16b7438701cddfbf564", "sha256": "fcc02e49bb54771af51470e85611067a8b6718d0126af09da34bbb1e12096f5f", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-core/1.18.0/grpc-core-1.18.0.jar", "source": {"sha1": "2bc455b3b6dc83ae095ccffeb9773bac7e839497", "sha256": "5f88ad25402cdad5aeb3ff761908324dca0f13722e4926310cdfd377aa0b0074", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-core/1.18.0/grpc-core-1.18.0-sources.jar"} , "name": "io_grpc_grpc_core", "actual": "@io_grpc_grpc_core//jar", "bind": "jar/io/grpc/grpc_core"},
    {"artifact": "io.grpc:grpc-netty-shaded:1.18.0", "lang": "java", "sha1": "e202aa0b36800e60ca87ec05a2ce4b240f69de09", "sha256": "e78a7d8ed465fdb0f03d7af867b2e6664f07b05c95445485e5873280183cb37a", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-netty-shaded/1.18.0/grpc-netty-shaded-1.18.0.jar", "source": {"sha1": "911a65601c82fae874953059cf56e798acf3c4a4", "sha256": "1cc93cb818519acf71414f849031b3248c0dfb18d9ffaef2824563a264150019", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-netty-shaded/1.18.0/grpc-netty-shaded-1.18.0-sources.jar"} , "name": "io_grpc_grpc_netty_shaded", "actual": "@io_grpc_grpc_netty_shaded//jar", "bind": "jar/io/grpc/grpc_netty_shaded"},
    {"artifact": "io.grpc:grpc-protobuf-lite:1.18.0", "lang": "java", "sha1": "4ce979e12da19aaef862c1f48385cb5cf69d61d7", "sha256": "108a16c2b70df636ee78976916d6de0b8f393b2b45b5b62909fc03c1a928ea9b", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-protobuf-lite/1.18.0/grpc-protobuf-lite-1.18.0.jar", "source": {"sha1": "78d3dbc3e28840060112037869ea823bf98fd317", "sha256": "14276525b70eab567b465eb355b99b366eafefa139cb822ccf055603dae0df47", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-protobuf-lite/1.18.0/grpc-protobuf-lite-1.18.0-sources.jar"} , "name": "io_grpc_grpc_protobuf_lite", "actual": "@io_grpc_grpc_protobuf_lite//jar", "bind": "jar/io/grpc/grpc_protobuf_lite"},
    {"artifact": "io.grpc:grpc-protobuf:1.18.0", "lang": "java", "sha1": "74d794cf9b90b620e0ad698008abc4f55c1ca5e2", "sha256": "ab714cf4fec2c588f9d8582c2844485c287afa2a3a8da280c62404e312b2d2b1", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-protobuf/1.18.0/grpc-protobuf-1.18.0.jar", "source": {"sha1": "1c6791c6af55f13db7022cb3810fd56f4a2359b9", "sha256": "f7fd8af3f00bbbeafcabbb55bd8fca5ba0abc0e5d530c60dd53bc20691dc0ac7", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-protobuf/1.18.0/grpc-protobuf-1.18.0-sources.jar"} , "name": "io_grpc_grpc_protobuf", "actual": "@io_grpc_grpc_protobuf//jar", "bind": "jar/io/grpc/grpc_protobuf"},
    {"artifact": "io.grpc:grpc-stub:1.18.0", "lang": "java", "sha1": "5e4dbf944814d49499e3cbd9846ef58f629b5f32", "sha256": "6509fbbcf953f9c426f891021279b2fb5fb21a27c38d9d9ef85fc081714c2450", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-stub/1.18.0/grpc-stub-1.18.0.jar", "source": {"sha1": "9cf8435d715c47ad7cd2981a227b61253a5d9e63", "sha256": "dfc0cec6c4de6d45ba49782aaf1ffadb9feaaddda66cf74109050e6158c00e5f", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/grpc/grpc-stub/1.18.0/grpc-stub-1.18.0-sources.jar"} , "name": "io_grpc_grpc_stub", "actual": "@io_grpc_grpc_stub//jar", "bind": "jar/io/grpc/grpc_stub"},
    {"artifact": "io.opencensus:opencensus-api:0.18.0", "lang": "java", "sha1": "b89a8f8dfd1e1e0d68d83c82a855624814b19a6e", "sha256": "45421ffe95271aba94686ed8d4c5070fe77dc2ff0b922688097f0dd40f1931b1", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/opencensus/opencensus-api/0.18.0/opencensus-api-0.18.0.jar", "source": {"sha1": "0bf0e4d54c976a0f18331b0f3ebad3105dd25da7", "sha256": "598043929a08e875db037bb172dce62b8cbc13f51f811b62643a52ac5b96fc32", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/opencensus/opencensus-api/0.18.0/opencensus-api-0.18.0-sources.jar"} , "name": "io_opencensus_opencensus_api", "actual": "@io_opencensus_opencensus_api//jar", "bind": "jar/io/opencensus/opencensus_api"},
    {"artifact": "io.opencensus:opencensus-contrib-grpc-metrics:0.18.0", "lang": "java", "sha1": "8e90fab2930b6a0e67dab48911b9c936470d43dd", "sha256": "1f90585e777b1e0493dbf22e678303369a8d5b7c750b4eda070a34ca99271607", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/opencensus/opencensus-contrib-grpc-metrics/0.18.0/opencensus-contrib-grpc-metrics-0.18.0.jar", "source": {"sha1": "73c486b3124f087180853de76654bd67e646d55c", "sha256": "3bb1c879c1de76b20a1dedef5f5e9873bcb0c8b8d4991d6b3710539160d9e8ba", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/io/opencensus/opencensus-contrib-grpc-metrics/0.18.0/opencensus-contrib-grpc-metrics-0.18.0-sources.jar"} , "name": "io_opencensus_opencensus_contrib_grpc_metrics", "actual": "@io_opencensus_opencensus_contrib_grpc_metrics//jar", "bind": "jar/io/opencensus/opencensus_contrib_grpc_metrics"},
    {"artifact": "org.checkerframework:checker-compat-qual:2.5.2", "lang": "java", "sha1": "dc0b20906c9e4b9724af29d11604efa574066892", "sha256": "d7291cebf5e158d169807ae49d4b16ff672986f0c6d803e5f207c40cb61ef982", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/checkerframework/checker-compat-qual/2.5.2/checker-compat-qual-2.5.2.jar", "source": {"sha1": "420d8098e865794623d5e412445f8d2adea0bd54", "sha256": "27e52153bbdf10adcd39eaf3acdcb7a27e70dce22545a0a37121fa02429322ca", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/checkerframework/checker-compat-qual/2.5.2/checker-compat-qual-2.5.2-sources.jar"} , "name": "org_checkerframework_checker_compat_qual", "actual": "@org_checkerframework_checker_compat_qual//jar", "bind": "jar/org/checkerframework/checker_compat_qual"},
    {"artifact": "org.codehaus.mojo:animal-sniffer-annotations:1.17", "lang": "java", "sha1": "f97ce6decaea32b36101e37979f8b647f00681fb", "sha256": "92654f493ecfec52082e76354f0ebf87648dc3d5cec2e3c3cdb947c016747a53", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17.jar", "source": {"sha1": "8fb5b5ad9c9723951b9fccaba5bb657fa6064868", "sha256": "2571474a676f775a8cdd15fb9b1da20c4c121ed7f42a5d93fca0e7b6e2015b40", "repository": "https://repo.maven.apache.org/maven2/", "url": "https://repo.maven.apache.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17-sources.jar"} , "name": "org_codehaus_mojo_animal_sniffer_annotations", "actual": "@org_codehaus_mojo_animal_sniffer_annotations//jar", "bind": "jar/org/codehaus/mojo/animal_sniffer_annotations"},
    ]

def maven_dependencies(callback = jar_artifact_callback):
    for hash in list_dependencies():
        callback(hash)
