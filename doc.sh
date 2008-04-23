########################################################
# generate sojamo distribution
# 2006 by andreas schlegel 
# $JAVA is the path to the java 1.5 commands folder
#
########################################################



#rm -R ./src/osc/documentation/
#rm -R ./src/net/documentation/

javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source oscP5
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source netP5
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source controlP5

javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.net
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.osc
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.control
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.chat
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.chat.irc
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.chat.aim
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.drop
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.texture
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.texture.quicktime
javadoc -doclet prodoc.StartDoclet -docletpath ./lib/prodoc/  -sourcepath ./source sojamo.texture.ffmpeg

mkdir ./jar/oscP5
mkdir ./jar/oscP5/library
cp ./jar/oscP5.jar ./jar/oscP5/library/
cp -r ./source/oscP5/documentation ./jar/oscP5/
cp -r ./src/oscP5/docuImages ./jar/oscP5/documentation
cp -r ./source/oscP5/examples ./jar/oscP5/

mkdir ./jar/netP5
mkdir ./jar/netP5/library
cp ./jar/netP5.jar ./jar/netP5/library/
cp -r ./source/netP5/documentation ./jar/netP5/
cp -r ./src/netP5/docuImages ./jar/netP5/documentation
cp -r ./source/netP5/examples ./jar/netP5/


mkdir ./jar/controlP5
mkdir ./jar/controlP5/library
cp ./jar/controlP5.jar ./jar/controlP5/library/
cp -r ./source/controlP5/documentation ./jar/controlP5/
cp -r ./src/controlP5/docuImages ./jar/controlP5/documentation
cp -r ./source/controlP5/examples ./jar/controlP5/



mkdir ./jar/sOSC
mkdir ./jar/sOSC/library
cp ./jar/sOSC.jar ./jar/sOSC/library/
cp -r ./source/sojamo/osc/documentation ./jar/sOSC/
cp -r ./src/sojamo/osc/docuImages ./jar/sOSC/documentation
cp -r ./source/sojamo/osc/examples ./jar/sOSC/


mkdir ./jar/sControl
mkdir ./jar/sControl/library
cp ./jar/sControl.jar ./jar/sControl/library/
cp -r ./source/sojamo/control/documentation ./jar/sControl/
cp -r ./src/sojamo/control/docuImages ./jar/sControl/documentation
cp -r ./source/sojamo/control/examples ./jar/sControl/


mkdir ./jar/sDrop
mkdir ./jar/sDrop/library
cp ./jar/sDrop.jar ./jar/sDrop/library/
cp -r ./source/sojamo/drop/documentation ./jar/sDrop/
cp -r ./src/sojamo/drop/docuImages ./jar/sDrop/documentation
cp -r ./source/sojamo/drop/examples ./jar/sDrop/


mkdir ./jar/sNetwork
mkdir ./jar/sNetwork/library
cp ./jar/sNetwork.jar ./jar/sNetwork/library/
cp -r ./source/sojamo/net/documentation ./jar/sNetwork/
cp -r ./source/sojamo/net/examples ./jar/sNetwork/


mkdir ./jar/sChat
mkdir ./jar/sChat/library
cp ./jar/sChat.jar ./jar/sChat/library/
cp -r ./source/sojamo/chat/documentation ./jar/sChat/
cp -r ./source/sojamo/chat/irc/documentation ./jar/sChat/documentation/irc
cp -r ./source/sojamo/chat/aim/documentation ./jar/sChat/documentation/aim
cp -r ./source/sojamo/chat/examples ./jar/sChat/


mkdir ./jar/sTexture
mkdir ./jar/sTexture/library
cp ./jar/sTexture.jar ./jar/sTexture/library/
cp -r ./source/sojamo/texture/documentation ./jar/sTexture/
cp -r ./src/sojamo/texture/docuImages ./jar/sTexture/documentation
cp -r ./source/sojamo/texture/examples ./jar/sTexture/


########################################################
# distribution
########################################################
mkdir ./distribution

cd ./

cp -r jar/oscP5 distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf distribution/oscP5.tgz distribution/oscP5
cd distribution
zip -r oscP5 oscP5
cd ..


cp -r jar/netP5 distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/netP5.tgz ./distribution/netP5
cd ./distribution
zip -r netP5 netP5
cd ..


cp -r jar/controlP5 distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/controlP5.tgz ./distribution/controlP5
cd ./distribution
zip -r controlP5 controlP5
cd ..



cp -r jar/sOSC distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sOSC.tgz ./distribution/sOSC
cd ./distribution
zip -r sOSC sOSC
cd ..

cp -r jar/sNet distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sNet.tgz ./distribution/sNet
cd ./distribution
zip -r sNet sNet
cd ..

cp -r jar/sControl distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sControl.tgz ./distribution/sControl
cd ./distribution
zip -r sControl sControl
cd ..



cp -r jar/sDrop distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sDrop.tgz ./distribution/sDrop
cd ./distribution
zip -r sDrop sDrop
cd ..

cp -r jar/sChat distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sChat.tgz ./distribution/sChat
cd ./distribution
zip -r sChat sChat
cd ..


cp -r jar/sTexture distribution
find ./distribution -name .DS_Store -ls -exec rm {} \;
find ./distribution -name .svn -ls -exec rm -rf {} \;
tar cvzf ./distribution/sTexture.tgz ./distribution/sTexture
cd ./distribution
zip -r sTexture sTexture
cd ..

find . -name .DS_Store -ls -exec rm {} \;

########################################################
# cleanup
########################################################
rm -rf ../../c
mkdir ../../c
mv jar ../../c/
mv source ../../c/
mv build ../../c/
mv distribution ../../c/
