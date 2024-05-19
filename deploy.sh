if [ -d ~/dist ];then
    echo "remove deprecated dist" && rm -rf ~/dist
fi
if [ ! -d docs/.vuepress/dist/ ];then
    echo "run build cmd first!"
    exit 1
fi
cp -r docs/.vuepress/dist/ ~/dist/