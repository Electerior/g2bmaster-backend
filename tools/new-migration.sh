#!/usr/bin/env bash
# 새 Flyway 마이그레이션 파일을 만든다.
#
#   ./tools/new-migration.sh d2b_staging_layer
#
# 버전을 사람이 고르지 않는 것이 요점이다. 순번(V15, V16…)은 브랜치가 갈라져 있는 동안
# 서로 같은 번호를 집는 사고를 반복해서 냈다 — feat/price-catalog 가 V9/V10 을,
# feat/notice-search-index 가 V11~V14 를 서로 모른 채 잡았다. 번호는 머지하는 순간에야
# 부딪히고, 그때는 이미 main 이 깨져 있다.
#
# 초 단위 타임스탬프는 같은 초에 두 사람이 파일을 만들지 않는 한 충돌하지 않는다.
# 순번 V1~V14 는 이 규칙 이전의 것이라 그대로 두었다 — 이미 적용된 DB 의
# flyway_schema_history 와 파일명이 어긋나면 앱이 기동하지 않기 때문이다.
set -euo pipefail

desc="${1:-}"
if [ -z "$desc" ]; then
	echo "사용법: $0 <설명>    예: $0 d2b_staging_layer" >&2
	exit 2
fi

# 설명은 파일명이자 flyway_schema_history 의 description 이 된다. 공백·하이픈이 섞이면
# 기존 12개와 읽는 규칙이 달라지므로 소문자 snake_case 로 닫아 둔다.
if ! [[ "$desc" =~ ^[a-z][a-z0-9_]*$ ]]; then
	echo "설명은 소문자·숫자·밑줄만 쓴다(공백·하이픈·대문자 금지): $desc" >&2
	exit 2
fi

dir="$(cd "$(dirname "$0")/.." && pwd)/src/main/resources/db/migration"

# 팀도 앱(Asia/Seoul)도 KST 로 도니 파일명 시각도 같은 기준으로 읽히게 둔다.
ver=$(TZ=Asia/Seoul date +%Y%m%d%H%M%S)
file="$dir/V${ver}__${desc}.sql"

if [ -e "$file" ]; then
	echo "같은 초에 만들어진 파일이 이미 있다 — 1초 뒤에 다시 실행할 것: $file" >&2
	exit 1
fi

cat > "$file" <<EOF
-- ============================================================
-- V${ver}__${desc}.sql
-- (무엇을 하는 마이그레이션인지 한 줄)
--
-- 왜 필요한가:
--
-- 주의:
--   - 머지된 뒤에는 이 파일을 고치지 않는다. Flyway 가 체크섬을 들고 있어서,
--     이미 적용한 사람의 앱이 기동하지 않는다. 정정은 새 파일로 한다.
--   - ENUM 은 말미 append 만 한다(INSTANT). 재배열·삭제는 ALGORITHM=COPY 다.
-- ============================================================

EOF

echo "$file"
