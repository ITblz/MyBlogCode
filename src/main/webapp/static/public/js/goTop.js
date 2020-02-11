window.onload = function() {
        //获取回到顶部的按钮
        var btn = document.getElementById('goTopBtn');
        var timer = null;

        // 标识是否清除定时器，为了实现在回到顶部的过程中通过滚动一下鼠标实现清除定时器从而达到滚动的目的
        var isTop = true;

        // 获取页面可视区高度，从而判断返回顶部按钮是否出现
        var cHeight = document.documentElement.clientHeight;

        window.onscroll = function() {
            // 让返回顶部按钮在第二屏才开始出现
            var osTop = document.documentElement.scrollTop || document.body.scrollTop;
            if (osTop >= cHeight) {
                btn.style.display = 'block';
            } else {
                btn.style.display = 'none';
            }

            if (!isTop) {
                clearInterval(timer);
            }
            isTop = false;
        }

        btn.onclick = function() {
            // 设置定时器
            timer = setInterval(function() {
                // 获取滚动条距离顶部的高度
                var osTop = document.documentElement.scrollTop || document.body.scrollTop;
                // 越滚越慢，设置成负数是为了防止减不到0
                var ispeed = Math.floor(-osTop / 6);
                document.documentElement.scrollTop = document.body.scrollTop = osTop + ispeed;

                isTop = true; // 这里记得设置，不然滚一次就停止了

                if (osTop == 0) {
                    clearInterval(timer);
                }
            }, 30);
        };
    }