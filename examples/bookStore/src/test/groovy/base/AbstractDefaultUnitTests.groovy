package base

import abstractclass.AbstractClass
import abstractclass.AbstractSubClass

import abstractclass.AnotherConcreteSubClass
import abstractclass.ConcreteSubClass
import grails.buildtestdata.TestData
import grails.buildtestdata.TestDataConfigurationHolder
import grails.buildtestdata.BuildDataTest

import spock.lang.Specification

class AbstractDefaultUnitTests extends Specification implements BuildDataTest {
    void setup() {
        TestDataConfigurationHolder.reset()

        // Since we are changing out the subclass defaults, prevent any caching
        TestData.clear()
    }

    void testBuildWithNoDefault() {
        TestDataConfigurationHolder.config.abstractDefault = [:]

        when:
        build(AbstractClass)

        then: "this is no longer supported in BTD 3.3 and later, specific default required"
        thrown(RuntimeException)
    }

    void testSuccessfulBuildWithDefault() {
        TestDataConfigurationHolder.config.abstractDefault = ['abstractclass.AbstractSubClass': ConcreteSubClass]
        mockDomain(AbstractSubClass)

        when:
        def obj = build(AbstractSubClass)

        then:
        obj instanceof ConcreteSubClass
    }

    void testSuccessfulBuildWithDifferentDefault() {
        TestDataConfigurationHolder.config.abstractDefault = ['abstractclass.AbstractSubClass': AnotherConcreteSubClass]
        mockDomain(AbstractSubClass)

        when:
        def obj = build(AbstractSubClass)

        then:
        obj instanceof AnotherConcreteSubClass
    }
}
