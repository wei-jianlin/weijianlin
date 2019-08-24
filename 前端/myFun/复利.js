/**
 * P：现值（Present Value），或叫期初金额。
 * i：利率或折现率
 * N：计息期数
 * @return {[type]} [description]
 */
function a(p,i,n){
    var f = p * Math.pow(1 + i,n);
    return f;
}