package prus.justweatherapp.local.db.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias StringAsLong = @Serializable(with = StringAsLongSerializer::class) String

class StringAsLongSerializer : KSerializer<String> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("StringToLong", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): String {
        return decoder.decodeLong().toString()
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeLong(value.toLong())
    }
}