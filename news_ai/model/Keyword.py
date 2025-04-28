from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional

@dataclass
class Keyword:
    keyword: str
    created_date: datetime
    news_article_id : int
    score : float
    id: Optional[int] = field(default=None)