########################################################
# generate sojamo distribution
# 2006 by andreas schlegel 
# $JAVA is the path to the java 1.5 commands folder
#
########################################################



javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./src sojamo.drop

#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.net
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.osc
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.control
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.chat
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.chat.irc
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.chat.aim
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.texture
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.texture.quicktime
#javadoc -doclet prodoc.StartDoclet -docletpath ../../libs/prodoc/  -sourcepath ./source sojamo.texture.ffmpeg

mkdir ./jar
mkdir ./jar/sDrop
mkdir ./jar/sDrop/library
cp ./jar/sDrop.jar ./jar/sDrop/library/
cp -r ./src/sojamo/drop/documentation ./jar/sDrop/
cp -r ./src/sojamo/drop/docuImages ./jar/sDrop/documentation
cp -r ./src/sojamo/drop/examples ./jar/sDrop/


########################################################
# distribution
########################################################
mkdir ./distribution

cd ./


cp -r jar/sDrop distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sDrop.tgz ./distribution/sDrop
cd ./distribution
zip -r sDrop sDrop
cd ..

find . -name .DS_Store -ls -exec rm {} \;

########################################################
# cleanup
########################################################
rm -rf ../sojamoCompiled
mkdir ../sojamoCompiled
mv jar ../sojamoCompiled
mv source ../sojamoCompiled
mv build ../sojamoCompiled
mv distribution ../sojamoCompiled

rm -r ./src/sojamo/drop/documentation

