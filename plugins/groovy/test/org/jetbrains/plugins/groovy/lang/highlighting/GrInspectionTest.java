// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.plugins.groovy.lang.highlighting;

import org.jetbrains.plugins.groovy.codeInspection.assignment.GroovyAssignabilityCheckInspection;
import org.jetbrains.plugins.groovy.codeInspection.assignment.GroovyResultOfAssignmentUsedInspection;
import org.jetbrains.plugins.groovy.codeInspection.bugs.*;
import org.jetbrains.plugins.groovy.codeInspection.confusing.*;
import org.jetbrains.plugins.groovy.codeInspection.control.GroovyTrivialConditionalInspection;
import org.jetbrains.plugins.groovy.codeInspection.control.GroovyTrivialIfInspection;
import org.jetbrains.plugins.groovy.codeInspection.control.GroovyUnnecessaryContinueInspection;
import org.jetbrains.plugins.groovy.codeInspection.control.GroovyUnnecessaryReturnInspection;
import org.jetbrains.plugins.groovy.codeInspection.declaration.GrMethodMayBeStaticInspection;
import org.jetbrains.plugins.groovy.codeInspection.exception.GroovyEmptyCatchBlockInspection;
import org.jetbrains.plugins.groovy.codeInspection.metrics.GroovyOverlyLongMethodInspection;
import org.jetbrains.plugins.groovy.codeInspection.noReturnMethod.MissingReturnInspection;
import org.jetbrains.plugins.groovy.codeInspection.untypedUnresolvedAccess.GrUnresolvedAccessInspection;
import org.jetbrains.plugins.groovy.codeInspection.untypedUnresolvedAccess.GroovyUntypedAccessInspection;

/**
 * @author Max Medvedev
 */
public class GrInspectionTest extends GrHighlightingTestBase {
  public void testDontSimplifyString() { doTest(new GroovyTrivialIfInspection(), new GroovyTrivialConditionalInspection()); }

  public void testSingleAllocationInClosure() { doTest(new GroovyResultOfObjectAllocationIgnoredInspection()); }

  public void testUnusedAllocationInClosure() { doTest(new GroovyResultOfObjectAllocationIgnoredInspection()); }

  public void testUsedLabel() { doTest(new GroovyLabeledStatementInspection()); }

  public void testOverlyLongMethodInspection() {
    GroovyOverlyLongMethodInspection inspection = new GroovyOverlyLongMethodInspection();
    inspection.m_limit = 5;
    doTest(inspection);
  }

  public void testRangeType() { doTest(new GroovyRangeTypeCheckInspection()); }

  public void testResolveMetaClass() { doTest(); }

  public void testResultOfAssignmentUsed() {
    GroovyResultOfAssignmentUsedInspection inspection = new GroovyResultOfAssignmentUsedInspection();
    inspection.inspectClosures = true;
    doTest(inspection);
  }

  public void testSuppressions() { doTest(new GrUnresolvedAccessInspection(), new GroovyUntypedAccessInspection()); }

  public void testInnerClassConstructorThis() {
    GroovyResultOfAssignmentUsedInspection inspection = new GroovyResultOfAssignmentUsedInspection();
    inspection.inspectClosures = true;
    doTest(true, true, true, inspection);
  }

  public void testUnnecessaryReturnInSwitch() { doTest(new GroovyUnnecessaryReturnInspection()); }

  public void testMemberShipOperatorCheck() { doTest(new GroovyInArgumentCheckInspection()); }

  public void testOctalInspection() { doTest(new GroovyOctalIntegerInspection()); }

  public void testClashingGetters() {
    doTestHighlighting("""
                         class Foo {

                           boolean <warning descr="Clash occurred: 'Getter getX' with 'Getter isX'">getX</warning>() { true }
                           boolean <warning descr="Clash occurred: 'Getter isX' with 'Getter getX'">isX</warning>() { false }

                           boolean getY() {true}

                           boolean isZ() {false}

                           boolean <warning descr="Clash occurred: 'Method getFoo(int x)' with 'Getter isFoo'">getFoo</warning>(int x = 5){}
                           boolean <warning descr="Clash occurred: 'Getter isFoo' with 'Method getFoo(int x)'">isFoo</warning>(){}
                         }

                         def result = new Foo().x""", true, false, false, ClashingGettersInspection.class);
  }

  public void testDeprecated() {
    doTestHighlighting("""
                         /**
                          @deprecated
                         */
                         class X {
                           @Deprecated
                           def foo(){}

                           public static void main() {
                             new <warning descr="'X' is deprecated">X</warning>().<warning descr="'foo' is deprecated">foo</warning>()
                           }
                         }""", true, false, false, GrDeprecatedAPIUsageInspection.class);
  }

  public void testDeprecated2() {
    doTestHighlighting("""
                         class X {
                           @Deprecated
                           def X(){}

                           public static void main() {
                             new <warning descr="'X' is deprecated">X</warning>()
                           }
                         }""", true, false, false, GrDeprecatedAPIUsageInspection.class);
  }

  public void testDeprecated3() {
    doTestHighlighting("""
                         class TestService {

                             @Deprecated
                             static void test() {}

                             static void test(String s) {}

                             static void main(String[] args) {
                                 test("")
                                 <warning>test</warning>()
                             }
                         }""", true, false, false, GrDeprecatedAPIUsageInspection.class);
  }

  public void testDeprecated4() {
    doTestHighlighting("""
                         class TestService {

                             static void test() {}
                            \s
                             @Deprecated
                             static void test(String s) {}

                             static void main(String[] args) {
                                 <warning>test</warning>("")
                                 test()
                             }
                         }""", true, false, false, GrDeprecatedAPIUsageInspection.class);
  }

  public void testSuppressedErrorInGroovyDoc() {
    doTestHighlighting("""
                         class Class2 {


                           /** dependency injection for {@link GrailsFilterInvocationDefinition} */
                           @SuppressWarnings("GroovyDocCheck")
                           static main(args) {}

                           /** dependency injection for {@link <error descr="Cannot resolve symbol 'GrailsFilterInvocationDefinition'">GrailsFilterInvocationDefinition</error>} */
                           static main2(args) {}
                         }""", GroovyDocCheckInspection.class);
  }

  public void testMissingReturnInBinaryOr() {
    doTestHighlighting("""
                         private boolean onWinOrMacOS_() {
                             OperatingSystem.isWindows() || OperatingSystem.isMacOsX()
                         }
                         private boolean onWinOrMacOS() {
                             if (true) {
                                 OperatingSystem.isWindows() || OperatingSystem.isMacOsX()
                            }
                         <warning descr="Not all execution paths return a value">}</warning>

                         """, MissingReturnInspection.class);
  }

  public void testMissingReturnInUnary() {
    doTestHighlighting("""
                         boolean foo(def list) {
                           !list
                         }

                         boolean bar(def list) {
                           if (list) !list
                         <warning descr="Not all execution paths return a value">}</warning>
                         """, MissingReturnInspection.class);
  }

  public void testMissingReturnInBinary() {
    doTestHighlighting("""
                         boolean foo(def list) {
                           !list && list
                         }

                         boolean bar(def list) {
                           if (list) !list && list
                         <warning descr="Not all execution paths return a value">}</warning>
                         """, MissingReturnInspection.class);
  }

  public void testPackageDefinition() {
    myFixture.addFileToProject("cde/bar.groovy", "//empty file");
    myFixture.addFileToProject("abc/foo.groovy", """
      <warning descr="Package name mismatch. Actual: 'cde', expected: 'abc'">package cde</warning>

      print 2
      """);
    myFixture.enableInspections(new GrPackageInspection());
    myFixture.testHighlighting(true, false, false, "abc/foo.groovy");
  }

  public void testPackageDefinition2() {
    myFixture.addFileToProject("abc/foo.groovy", """
      <warning descr="Package name mismatch. Actual: 'cde', expected: 'abc'">package cde</warning>

      print 2
      """);
    myFixture.enableInspections(new GrPackageInspection());
    myFixture.testHighlighting(true, false, false, "abc/foo.groovy");
  }

  public void testUntypedAccess() { doTest(new GroovyUntypedAccessInspection()); }

  public void testMethodMayBeStaticForCategoryClasses() {
    doTestHighlighting("""
                         class Cat{
                           def <warning descr="Method may be static">foo</warning>() {
                               print 2
                           }
                         }

                         @groovy.lang.Category(Cat)
                         class I{
                             def foo() {
                               print 2
                             }
                         }
                         """, GrMethodMayBeStaticInspection.class);
  }

  public void testDelegatesTo() {
    doTestHighlighting("""


                         def with1(@DelegatesTo.Target() Object target, @DelegatesTo() Closure arg) { //unused
                             arg.delegate = target
                             arg()
                         }

                         def with2(@<warning descr="@Target is unused">DelegatesTo.Target</warning>('abc') Object target, @DelegatesTo() Closure arg) { //unused
                             arg.delegate = target
                             arg()
                         }

                         def with3(@DelegatesTo.Target('abc') Object target, @DelegatesTo(target='abc') Closure arg) { //unused
                             arg.delegate = target
                             arg()
                         }

                         def with4(@<warning descr="@Target is unused">DelegatesTo.Target</warning>('abcd') Object target, @DelegatesTo(target=<warning descr="Target 'abc' does not exist">'abc'</warning>) Closure arg) { //unused
                             arg.delegate = target
                             arg()
                         }

                         def with5(@<warning descr="@Target is unused">DelegatesTo.Target</warning>() Object target, @DelegatesTo(target=<warning descr="Target 'abc' does not exist">'abc'</warning>) Closure arg) { //unused
                             arg.delegate = target
                             arg()
                         }

                         def with6(@<warning descr="@Target is unused">DelegatesTo.Target</warning>() Object target, @DelegatesTo(String) Closure arg) {
                             arg.delegate = target
                             arg()
                         }

                         """, DelegatesToInspection.class);
  }

  public void testUnnecessaryContinue() {
    doTestHighlighting("""

                         for(i in []) {
                           print 2
                           <warning descr="continue is unnecessary as the last statement in a loop">continue</warning>
                         }

                         for(i in []) {
                           print 2
                           continue
                           print 3
                         }

                         for(i in []) {
                           print 2
                           switch(i) {
                             case not_last:
                               continue
                             case last:
                               <warning descr="continue is unnecessary as the last statement in a loop">continue</warning>
                           }
                         }

                         for(i in []) {
                           if (cond) {
                               print 2
                               <warning descr="continue is unnecessary as the last statement in a loop">continue</warning>
                           }
                           else {
                             continue
                             print 4
                           }
                         }

                         for (i in []) {
                           if (cond) {
                             continue
                           }
                           return
                         }

                         for (i in []) {
                           if (cond) {
                             <warning descr="continue is unnecessary as the last statement in a loop">continue</warning>
                           } else {
                             return
                           }
                         }
                         """, GroovyUnnecessaryContinueInspection.class);
  }

  public void testEmptyCatchBlock1() {
    doTestHighlighting("""

                         try{} <warning descr="Empty 'catch' block">catch</warning>(IOException e) {}
                         try{} catch(IOException ignored) {}
                         try{} catch(IOException ignore) {}
                         try{} catch(IOException e) {/*comment*/}
                         """, GroovyEmptyCatchBlockInspection.class);
  }

  public void testEmptyCatchBlock2() {
    GroovyEmptyCatchBlockInspection inspection = new GroovyEmptyCatchBlockInspection();
    inspection.myIgnore = false;
    myFixture.enableInspections(inspection);
    doTestHighlighting("try{} <warning descr=\"Empty 'catch' block\">catch</warning>(IOException ignored) {}");
  }

  public void testEmptyCatchBlock3() {
    GroovyEmptyCatchBlockInspection inspection = new GroovyEmptyCatchBlockInspection();
    inspection.myIgnore = false;
    myFixture.enableInspections(inspection);
    doTestHighlighting("try{} <warning descr=\"Empty 'catch' block\">catch</warning>(IOException ignored) {}");
  }

  public void testEmptyCatchBlock4() {
    GroovyEmptyCatchBlockInspection inspection = new GroovyEmptyCatchBlockInspection();
    inspection.myCountCommentsAsContent = false;
    myFixture.enableInspections(inspection);
    doTestHighlighting("try{} <warning descr=\"Empty 'catch' block\">catch</warning>(IOException e) {/*comment*/}");
  }

  public void testInvokingMethodReferenceWithDefaultParameters() { doTest(new GroovyAssignabilityCheckInspection()); }
}
