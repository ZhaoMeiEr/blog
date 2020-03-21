let mapleader=" "
syntax on
"syntax enable
set number
set cursorline
set wrap
set showcmd
set wildmenu

set t_Co=256
set nocompatible
filetype on 
filetype indent on
filetype plugin on
filetype plugin indent on
let &t_ut=''
set expandtab
set tabstop=2
set shiftwidth=2
set softtabstop=2
" 设置不同模式下光标的样式
" let &t_SI =  "\<Esc>]50;CursorShape=1\x7"
" let &t_SR =  "\<Esc>]50;CursorShape=2\x7"
" let &t_EI =  "\<Esc>]50;CursorShape=0\x7"
" 每次保存关闭后 再次打开时 光标定位到上一次编辑的位置
au BufReadPost * if line("'\"") > 1 && line("'\"") <= line ("$") | exe "normal! g'\"" | endif

" 保证光标上面和下面有5行可以看到
set scrolloff=5

set hlsearch
exec "nohlsearch"
set incsearch
set ignorecase
set smartcase

noremap <LEADER><CR> :nohlsearch<CR>
noremap h i
noremap H I
noremap i k
noremap j h
noremap k j

map s <nop>
map S :w<CR>
map Q :q<CR>
map R :source $MYVIMRC<CR>

" 分屏快捷键
" sl -> 左右分屏 光标放在右边屏幕
map sl :set splitright<CR>:vsplit<CR>  
" sj -> 左右分屏 光标放在左边屏幕
map sj :set nosplitright<CR>:vsplit<CR>
" si -> 上下分屏 光标放在上边屏幕
map si :set nosplitbelow<CR>:split<CR>
" sk -> 上下分屏 光标放在下边屏幕
map sk :set splitbelow<CR>:split<CR>

" 空格 + i 光标移到上边屏幕
map <LEADER>i <C-w>k
" 空格 + k 光标移到下边屏幕
map <LEADER>k <C-w>j
" 空格 + j 光标移动到左边屏幕
map <LEADER>j <C-w>h
" 空格 + l 光标移动到右边屏幕
map <LEADER>l <C-w>l
" sv -> 从左右分屏切换为上下分屏
map sv <C-w>t<C-w>K
" sh -> 从上下分屏切换为左右分屏
map sh <C-w>t<C-w>L

" 标签快捷键
" tt -> 打开一个新的标签页
map tt :tabe<CR>
" tj -> 移动到前一个标签页
map tj :-tabnext<CR>
" tl -> 移动到后一个标签页
map tl :+tabnext<CR>

" 插件管理器
call plug#begin('~/.vim/plugged')
  " 底部提示插件
  Plug 'vim-airline/vim-airline'

  " 颜色插件
  Plug 'liuchengxu/space-vim-dark'

  " 文件列表插件 NERDTree
  Plug 'preservim/nerdtree'

  " NERDTree-git 给NERDTree增加git图标
  Plug 'Xuyuanp/nerdtree-git-plugin'

  " syntastic 代码错误提示
  Plug 'vim-syntastic/syntastic'
  " Ale 代码错误提示
  " Plug 'dense-analysis/ale'
  " YouCompleteMe 代码补全插件
  Plug 'ycm-core/YouCompleteMe'
call plug#end()

" 应用颜色插件 
colorscheme space-vim-dark

" NERDTree 文件列表插件配置
map ff :NERDTreeToggle<CR>
let NERDTreeMapOpenExpl = ""
let NERDTreeMapUpdir = ""
let NERDTreeMapUpdirKeepOpen = "l"
let NERDTreeMapOpenSplit = ""
let NERDTreeOpenVSplit = ""
let NERDTreeMapActivateNode = "i"
let NERDTreeMapOpenInTab = "o"
let NERDTreeMapPreview = ""
let NERDTreeCloseDir = "n"
let NERDTreeMapChangeRoot = "y"

" NERDTree-git 配置
let g:NERDTreeIndicatorMapCustom = {
    \ "Modified"  : "✹",
    \ "Staged"    : "✚",
    \ "Untracked" : "✭",
    \ "Renamed"   : "➜",
    \ "Unmerged"  : "═",
    \ "Deleted"   : "✖",
    \ "Dirty"     : "✗",
    \ "Clean"     : "✔︎",
    \ "Unknown"   : "?"
    \ }

" syntastic 代码错误提示配置
set statusline+=%#warningmsg#
set statusline+=%{SyntasticStatuslineFlag()}
set statusline+=%*

let g:syntastic_always_populate_loc_list = 1
let g:syntastic_auto_loc_list = 1
let g:syntastic_check_on_open = 1
let g:syntastic_check_on_wq = 0
