package defpackage;

import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: m0  reason: default package */
public final class m0 {
    public static byte[] adjustRequestData(byte[] bArr) {
        if (Util.a >= 27) {
            return bArr;
        }
        return Util.getUtf8Bytes(Util.fromUtf8Bytes(bArr).replace('+', '-').replace('/', '_'));
    }

    public static byte[] adjustResponseData(byte[] bArr) {
        String str;
        if (Util.a >= 27) {
            return bArr;
        }
        try {
            JSONObject jSONObject = new JSONObject(Util.fromUtf8Bytes(bArr));
            StringBuilder sb = new StringBuilder("{\"keys\":[");
            JSONArray jSONArray = jSONObject.getJSONArray("keys");
            for (int i = 0; i < jSONArray.length(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                sb.append("{\"k\":\"");
                sb.append(jSONObject2.getString("k").replace('-', '+').replace('_', '/'));
                sb.append("\",\"kid\":\"");
                sb.append(jSONObject2.getString("kid").replace('-', '+').replace('_', '/'));
                sb.append("\",\"kty\":\"");
                sb.append(jSONObject2.getString("kty"));
                sb.append("\"}");
            }
            sb.append("]}");
            return Util.getUtf8Bytes(sb.toString());
        } catch (JSONException e) {
            String valueOf = String.valueOf(Util.fromUtf8Bytes(bArr));
            if (valueOf.length() != 0) {
                str = "Failed to adjust response data: ".concat(valueOf);
            } else {
                str = new String("Failed to adjust response data: ");
            }
            Log.e("ClearKeyUtil", str, e);
            return bArr;
        }
    }
}
