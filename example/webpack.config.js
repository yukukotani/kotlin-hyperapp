const path = require('path');

const KotlinWebpackPlugin = require('@jetbrains/kotlin-webpack-plugin');

module.exports = {
    mode: 'development',
    entry: "kotlinApp",

    output: {
        // 出力するファイル名
        filename: 'bundle.js',
        // 出力先のパス（絶対パスを指定する必要がある）
        path: path.join(__dirname, 'build/js')
    },

    resolve: {
        modules: [
            './node_modules',
            './kotlin_build',
        ],
        extensions: [".js"]
    },
    
    plugins: [
        new KotlinWebpackPlugin({
            src: __dirname + '/src',
            librariesAutoLookup: true,
        })
    ]
};
