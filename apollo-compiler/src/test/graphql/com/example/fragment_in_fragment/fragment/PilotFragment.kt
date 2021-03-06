// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.fragment_in_fragment.fragment

import com.apollographql.apollo.api.GraphqlFragment
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.internal.ResponseFieldMapper
import com.apollographql.apollo.api.internal.ResponseFieldMarshaller
import com.apollographql.apollo.api.internal.ResponseReader
import kotlin.Array
import kotlin.String
import kotlin.Suppress

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter")
data class PilotFragment(
  val __typename: String = "Person",
  /**
   * The name of this person.
   */
  val name: String?,
  /**
   * A planet that this person was born on or inhabits.
   */
  val homeworld: Homeworld?
) : GraphqlFragment {
  override fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller.invoke { writer ->
    writer.writeString(RESPONSE_FIELDS[0], this@PilotFragment.__typename)
    writer.writeString(RESPONSE_FIELDS[1], this@PilotFragment.name)
    writer.writeObject(RESPONSE_FIELDS[2], this@PilotFragment.homeworld?.marshaller())
  }

  companion object {
    private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.forString("__typename", "__typename", null, false, null),
        ResponseField.forString("name", "name", null, true, null),
        ResponseField.forObject("homeworld", "homeworld", null, true, null)
        )

    val FRAGMENT_DEFINITION: String = """
        |fragment pilotFragment on Person {
        |  __typename
        |  name
        |  homeworld {
        |    __typename
        |    ...planetFragment
        |  }
        |}
        """.trimMargin()

    operator fun invoke(reader: ResponseReader): PilotFragment = reader.run {
      val __typename = readString(RESPONSE_FIELDS[0])!!
      val name = readString(RESPONSE_FIELDS[1])
      val homeworld = readObject<Homeworld>(RESPONSE_FIELDS[2]) { reader ->
        Homeworld(reader)
      }
      PilotFragment(
        __typename = __typename,
        name = name,
        homeworld = homeworld
      )
    }

    @Suppress("FunctionName")
    fun Mapper(): ResponseFieldMapper<PilotFragment> = ResponseFieldMapper { invoke(it) }
  }

  /**
   * A large mass, planet or planetoid in the Star Wars Universe, at the time of
   * 0 ABY.
   */
  data class Homeworld(
    val __typename: String = "Planet",
    val fragments: Fragments
  ) {
    fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller.invoke { writer ->
      writer.writeString(RESPONSE_FIELDS[0], this@Homeworld.__typename)
      this@Homeworld.fragments.marshaller().marshal(writer)
    }

    companion object {
      private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
          ResponseField.forString("__typename", "__typename", null, false, null),
          ResponseField.forString("__typename", "__typename", null, false, null)
          )

      operator fun invoke(reader: ResponseReader): Homeworld = reader.run {
        val __typename = readString(RESPONSE_FIELDS[0])!!
        val fragments = Fragments(reader)
        Homeworld(
          __typename = __typename,
          fragments = fragments
        )
      }

      @Suppress("FunctionName")
      fun Mapper(): ResponseFieldMapper<Homeworld> = ResponseFieldMapper { invoke(it) }
    }

    data class Fragments(
      val planetFragment: PlanetFragment
    ) {
      fun marshaller(): ResponseFieldMarshaller = ResponseFieldMarshaller.invoke { writer ->
        writer.writeFragment(this@Fragments.planetFragment.marshaller())
      }

      companion object {
        private val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.forFragment("__typename", "__typename", null)
            )

        operator fun invoke(reader: ResponseReader): Fragments = reader.run {
          val planetFragment = readFragment<PlanetFragment>(RESPONSE_FIELDS[0]) { reader ->
            PlanetFragment(reader)
          }!!
          Fragments(
            planetFragment = planetFragment
          )
        }

        @Suppress("FunctionName")
        fun Mapper(): ResponseFieldMapper<Fragments> = ResponseFieldMapper { invoke(it) }
      }
    }
  }
}
