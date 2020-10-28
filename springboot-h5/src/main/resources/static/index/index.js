var isDev = true;
API = function() {
    if (isDev) {
        return "http://192.168.3.91:9091";
    } else {
        return "";
    }
}