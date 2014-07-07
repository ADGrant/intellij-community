/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.jps.build;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.jps.build.AbstractIncrementalJpsTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("jps-plugin/testData/incremental")
public class IncrementalJpsTestGenerated extends AbstractIncrementalJpsTest {
    @TestMetadata("accessingFunctionsViaPackagePart")
    public void testAccessingFunctionsViaPackagePart() throws Exception {
        doTest("jps-plugin/testData/incremental/accessingFunctionsViaPackagePart/");
    }
    
    @TestMetadata("accessingPropertiesViaField")
    public void testAccessingPropertiesViaField() throws Exception {
        doTest("jps-plugin/testData/incremental/accessingPropertiesViaField/");
    }
    
    @TestMetadata("allConstants")
    public void testAllConstants() throws Exception {
        doTest("jps-plugin/testData/incremental/allConstants/");
    }
    
    public void testAllFilesPresentInIncremental() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("jps-plugin/testData/incremental"), Pattern.compile("^([^\\.]+)$"), false);
    }
    
    @TestMetadata("annotations")
    public void testAnnotations() throws Exception {
        doTest("jps-plugin/testData/incremental/annotations/");
    }
    
    @TestMetadata("classInlineFunctionChanged")
    public void testClassInlineFunctionChanged() throws Exception {
        doTest("jps-plugin/testData/incremental/classInlineFunctionChanged/");
    }
    
    @TestMetadata("classInlineFunctionUnchanged")
    public void testClassInlineFunctionUnchanged() throws Exception {
        doTest("jps-plugin/testData/incremental/classInlineFunctionUnchanged/");
    }
    
    @TestMetadata("classObjectConstantChanged")
    public void testClassObjectConstantChanged() throws Exception {
        doTest("jps-plugin/testData/incremental/classObjectConstantChanged/");
    }
    
    @TestMetadata("classRecreated")
    public void testClassRecreated() throws Exception {
        doTest("jps-plugin/testData/incremental/classRecreated/");
    }
    
    @TestMetadata("classSignatureChanged")
    public void testClassSignatureChanged() throws Exception {
        doTest("jps-plugin/testData/incremental/classSignatureChanged/");
    }
    
    @TestMetadata("classSignatureUnchanged")
    public void testClassSignatureUnchanged() throws Exception {
        doTest("jps-plugin/testData/incremental/classSignatureUnchanged/");
    }
    
    @TestMetadata("constantsUnchanged")
    public void testConstantsUnchanged() throws Exception {
        doTest("jps-plugin/testData/incremental/constantsUnchanged/");
    }
    
    @TestMetadata("dependencyClassReferenced")
    public void testDependencyClassReferenced() throws Exception {
        doTest("jps-plugin/testData/incremental/dependencyClassReferenced/");
    }
    
    @TestMetadata("filesExchangePackages")
    public void testFilesExchangePackages() throws Exception {
        doTest("jps-plugin/testData/incremental/filesExchangePackages/");
    }
    
    @TestMetadata("independentClasses")
    public void testIndependentClasses() throws Exception {
        doTest("jps-plugin/testData/incremental/independentClasses/");
    }
    
    @TestMetadata("inlineFunctionsCircularDependency")
    public void testInlineFunctionsCircularDependency() throws Exception {
        doTest("jps-plugin/testData/incremental/inlineFunctionsCircularDependency/");
    }
    
    @TestMetadata("multiplePackagesModified")
    public void testMultiplePackagesModified() throws Exception {
        doTest("jps-plugin/testData/incremental/multiplePackagesModified/");
    }
    
    @TestMetadata("ourClassReferenced")
    public void testOurClassReferenced() throws Exception {
        doTest("jps-plugin/testData/incremental/ourClassReferenced/");
    }
    
    @TestMetadata("packageConstantChanged")
    public void testPackageConstantChanged() throws Exception {
        doTest("jps-plugin/testData/incremental/packageConstantChanged/");
    }
    
    @TestMetadata("packageFileAdded")
    public void testPackageFileAdded() throws Exception {
        doTest("jps-plugin/testData/incremental/packageFileAdded/");
    }
    
    @TestMetadata("packageFileChangedPackage")
    public void testPackageFileChangedPackage() throws Exception {
        doTest("jps-plugin/testData/incremental/packageFileChangedPackage/");
    }
    
    @TestMetadata("packageFileChangedThenOtherRemoved")
    public void testPackageFileChangedThenOtherRemoved() throws Exception {
        doTest("jps-plugin/testData/incremental/packageFileChangedThenOtherRemoved/");
    }
    
    @TestMetadata("packageFileRemoved")
    public void testPackageFileRemoved() throws Exception {
        doTest("jps-plugin/testData/incremental/packageFileRemoved/");
    }
    
    @TestMetadata("packageFilesChangedInTurn")
    public void testPackageFilesChangedInTurn() throws Exception {
        doTest("jps-plugin/testData/incremental/packageFilesChangedInTurn/");
    }
    
    @TestMetadata("packageInlineFunctionAccessingField")
    public void testPackageInlineFunctionAccessingField() throws Exception {
        doTest("jps-plugin/testData/incremental/packageInlineFunctionAccessingField/");
    }
    
    @TestMetadata("packageInlineFunctionChanged")
    public void testPackageInlineFunctionChanged() throws Exception {
        doTest("jps-plugin/testData/incremental/packageInlineFunctionChanged/");
    }
    
    @TestMetadata("packageInlineFunctionFromOurPackage")
    public void testPackageInlineFunctionFromOurPackage() throws Exception {
        doTest("jps-plugin/testData/incremental/packageInlineFunctionFromOurPackage/");
    }
    
    @TestMetadata("packageInlineFunctionUnchanged")
    public void testPackageInlineFunctionUnchanged() throws Exception {
        doTest("jps-plugin/testData/incremental/packageInlineFunctionUnchanged/");
    }
    
    @TestMetadata("packageRecreated")
    public void testPackageRecreated() throws Exception {
        doTest("jps-plugin/testData/incremental/packageRecreated/");
    }
    
    @TestMetadata("packageRecreatedAfterRenaming")
    public void testPackageRecreatedAfterRenaming() throws Exception {
        doTest("jps-plugin/testData/incremental/packageRecreatedAfterRenaming/");
    }
    
    @TestMetadata("packageRemoved")
    public void testPackageRemoved() throws Exception {
        doTest("jps-plugin/testData/incremental/packageRemoved/");
    }
    
    @TestMetadata("returnTypeChanged")
    public void testReturnTypeChanged() throws Exception {
        doTest("jps-plugin/testData/incremental/returnTypeChanged/");
    }
    
    @TestMetadata("simpleClassDependency")
    public void testSimpleClassDependency() throws Exception {
        doTest("jps-plugin/testData/incremental/simpleClassDependency/");
    }
    
    @TestMetadata("soleFileChangesPackage")
    public void testSoleFileChangesPackage() throws Exception {
        doTest("jps-plugin/testData/incremental/soleFileChangesPackage/");
    }
    
    @TestMetadata("topLevelFunctionSameSignature")
    public void testTopLevelFunctionSameSignature() throws Exception {
        doTest("jps-plugin/testData/incremental/topLevelFunctionSameSignature/");
    }
    
    @TestMetadata("topLevelMembersInTwoFiles")
    public void testTopLevelMembersInTwoFiles() throws Exception {
        doTest("jps-plugin/testData/incremental/topLevelMembersInTwoFiles/");
    }
    
}
