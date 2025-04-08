from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional

@dataclass
class NewsArticleRelation:
    news_article_id : int
    related_news_article_id : int
    similarity : float
    id: Optional[int] = field(default=None)