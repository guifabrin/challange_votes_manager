const path = require('path')

module.exports = {
    entry: {
        main: path.resolve(__dirname, './src/main/resources/builders/js/index.js'),
    },
    output: {
        path: path.resolve(__dirname, './src/main/resources/static/js/'),
        filename: 'index.bundle.js',
    },
    module: {
        rules: [
            {
                test: /\.(scss|css)$/,
                use: ['style-loader', 'css-loader', 'sass-loader'],
            },
            {
                test: /\.html$/i,
                loader: 'html-loader',
            },
        ],
    },
    resolve: {
        alias: {
            vue: 'vue/dist/vue.js'
        },
    },
}