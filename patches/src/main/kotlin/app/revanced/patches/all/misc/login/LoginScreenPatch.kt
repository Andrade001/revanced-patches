package app.revanced.patches.all.misc.login

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.shared.misc.extension.sharedExtensionPatch
import app.revanced.patches.shared.misc.extension.extensionHook

private val loginHook = extensionHook(
    fingerprintBuilderBlock = {
        custom { method, classDef ->
            method.name == "onCreate" && classDef.endsWith("/MainActivity;")
        }
    }
)

val loginExtensionPatch = sharedExtensionPatch("login", loginHook)

@Suppress("unused")
val loginScreenPatch = bytecodePatch(
    name = "Login screen",
    description = "Shows a login screen and authenticates with an API.",
) {
    dependsOn(loginExtensionPatch)
    compatibleWith("com.google.android.youtube")
    finalize {
        loginHook.fingerprint.method.addInstruction(
            0,
            "invoke-static/range { p0 .. p0 }, Lapp/revanced/extension/login/LoginManager;->setContext(Landroid/content/Context;)V",
        )
    }
}
